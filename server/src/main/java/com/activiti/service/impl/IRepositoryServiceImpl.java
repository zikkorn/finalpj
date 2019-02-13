package com.activiti.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.activiti.base.entity.IProcess;
import com.activiti.base.entity.RepositoryModel;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.IRepositoryService;
import com.activiti.config.HessianService;
import com.activiti.mapper.CatalogMapper;
import com.activiti.mapper.ModelMapper;
import com.activiti.utils.JsonUtils;
import com.activiti.utils.ProblemsUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@HessianService
public class IRepositoryServiceImpl implements IRepositoryService {

    private final Logger logger = LoggerFactory.getLogger(IRepositoryServiceImpl.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CatalogMapper catalogMapper;
    /*@Autowired
    private EngineServices engineServices; */
    
    @Autowired
    private FormService formService;



    /**
     * 校验流程设计
     */
    @Override
    public List<ValidationError> validateProcess(String jsonXml) {
        ObjectNode modelNode = null;
        try {
            modelNode = (ObjectNode) new ObjectMapper().readTree(jsonXml.getBytes("UTF-8"));
        } catch (IOException e) {
            logger.error("流程实际不存在",e);
        }
        BpmnModel bpmModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        // activiti自带的检验接口
        List<ValidationError> list = repositoryService.validateProcess(bpmModel);
        if (list == null) {
            list = new ArrayList<>();
        }
        // 自定义检验
        list = this.customValidateProcess(jsonXml,bpmModel,list);
        // 汉化错误返回信息
        list = ProblemsUtils.handleValidationErrorToCN(list);
        logger.info("校验结果:" + JsonUtils.objectToJson(list));
        return list;
    }

    /**
     * 部署流程
     */
    @Override
    public ResultMessage deploy(String modelId) {
        try{
            Model modelData = repositoryService.getModel(modelId);
            byte[] source = repositoryService.getModelEditorSource(modelData.getId());
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(source);
            byte[] bpmnBytes = null;
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);

            // 校验 流程设计
            List<ValidationError> errorList = this.validateProcess(new String(source,"utf-8"));
            if(errorList != null && errorList.size() > 0){
                return new ResultMessage(1,"流程设计有误",errorList);
            }
//            if(model.getProcesses() == null || model.getProcesses().size() == 0){
//                return new ResultMessage(1,"流程设计不存在");
//            }
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);
            String processName = modelData.getName() + ".bpmn20.xml";
            DeploymentBuilder db = repositoryService.createDeployment().name(modelData.getName());
            Deployment deployment = db.addString(processName, new String(bpmnBytes,"utf-8")).deploy();
            logger.info(deployment.getId()+" "+deployment.getName());
            // 获取流程定义id
            String processDefId = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list().get(0).getId();
            // 更新流程启动者
            this.updateStart(processDefId,modelData.getMetaInfo());
        }catch (Exception e){
            logger.error("部署异常",e);
            return new ResultMessage(1,"部署异常");
        }
        return new ResultMessage(0,"查询成功");
    }

    /**
     * 保存流程启动者
     */
    @Override
    public int updateStart(String processDefId, String metaInfo) {
        Map<String,String> map = JsonUtils.jsonToObject(metaInfo,Map.class);
        Map<String,String> map2 = JsonUtils.jsonToObject(map.get("json"),Map.class);
        String userIds = map2.get("candidateUsers");
        String groupIds = map2.get("candidateGroups");
        int result = modelMapper.getProcessStarterCount(processDefId);
        if (result > 0){
            result = modelMapper.updateProcessStarter(userIds,groupIds,processDefId);
        }else{
            result = modelMapper.insertProcessStarter(UUID.randomUUID().toString(),userIds,groupIds,processDefId);
        }
        return result;
    }

    /**
     * 从流程设计数据中获取流程启动者
     */
    @Override
    public Map<String, String> getProcessStarterForJsonXml(String jsonXml) {
        Map<String,String> startableIdsMap = new HashMap<String,String>();
        if(StringUtils.isNotBlank(jsonXml)){
            HashMap<String, Object> map = JsonUtils.jsonToObject(jsonXml,HashMap.class);
            if(map.get("properties") != null){
                HashMap<String, Object> propertiesMap = (HashMap<String, Object>) map.get("properties");
                if(propertiesMap.get("usertaskassignment") != null && StringUtils.isNotBlank(propertiesMap.get("usertaskassignment").toString())){
                    Map<String,Map<String,ArrayList>> usertaskassignmentMap = (Map<String,Map<String,ArrayList>>)propertiesMap.get("usertaskassignment");
                    if(usertaskassignmentMap.get("assignment") != null && StringUtils.isNotBlank(usertaskassignmentMap.get("assignment").toString())){
                        Map<String,ArrayList> assignmentMap = usertaskassignmentMap.get("assignment");
                        if(assignmentMap.get("candidateUsers") != null && StringUtils.isNotBlank(assignmentMap.get("candidateUsers").toString())){
                            ArrayList<Map<String,String>> userList = assignmentMap.get("candidateUsers");
                            if(userList != null && userList.size() > 0){
                                StringBuffer sb = new StringBuffer();
                                for (Map<String, String> userMap : userList) {
                                    sb.append(userMap.get("value")).append(",");
                                }
                                startableIdsMap.put("candidateUsers",sb.substring(0,sb.length()-1));
                            }
                        }
                        if(assignmentMap.get("candidateGroups") != null && StringUtils.isNotBlank(assignmentMap.get("candidateGroups").toString())){
                            ArrayList<Map<String,String>> groupList = assignmentMap.get("candidateGroups");
                            if(groupList != null && groupList.size() > 0){
                                StringBuffer sb = new StringBuffer();
                                for (Map<String, String> groupMap : groupList) {
                                    sb.append(groupMap.get("value")).append(",");
                                }
                                startableIdsMap.put("candidateGroups",sb.substring(0,sb.length()-1));
                            }
                        }
                    }
                }
            }
        }
        return startableIdsMap;
    }

    /**
     * 从数据库中获取流程启动者
     */
    @Override
    public List<IProcess> getProcessStarterForDB(String processDefId) {
        List<IProcess> starterMapList = modelMapper.getProcessStarter(processDefId);
        return starterMapList;
    }

    /**
     * 查询用户或者角色的流程启动权限
     * @param userId        用户id或者角色id
     * @param processDefId 流程定义id
     * @return
     */
    @Override
    public List<IProcess> getProcessStartupAuth(String userId, String processDefId) {
        List<IProcess> procs = new ArrayList<>();
        List<IProcess> starterList = this.getProcessStarterForDB(processDefId);
        if(starterList != null && !starterList.isEmpty()){
            for (IProcess starter : starterList) {
                if(starter.getUserIds().contains(userId)){
                    procs.add(starter);
                }
            }
        }
        return procs;
    }

    /**
     * 分页查询用户或者角色可启动流程
     * @param userId        用户id或者角色id
     * @return
     */
    @Override
    public List<IProcess> getStartupProcess(String userId) {
        List<IProcess> starterList = modelMapper.getStartupProcess(userId);
        if(starterList != null && starterList.size() > 0){
        	for(IProcess p : starterList){
        		p.setFormKey(formService.getStartFormKey(p.getId()));
        	}
        }
        return starterList;
    }

    /**
     * 创建模型
     */
    @Override
    public String createModeler(String key, String name, String desc) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("key", key);
        editorNode.put("name", name);
        editorNode.put("desc", desc);
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        String description = StringUtils.isNotBlank(desc)?desc:"lutiannan---";
        modelObjectNode.put("key", key);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(StringUtils.isNotBlank(name)?name:"lutiannan");
        modelData.setKey(StringUtils.isNotBlank(key)?key:"12313123");

        //保存模型
        repositoryService.saveModel(modelData);
        try {
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("创建模型解析异常",e);
        }
//        response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        return modelData.getId();
    }


    /**
     * 自定义流程设计检验
     */
    private List<ValidationError> customValidateProcess(String jsonXml, BpmnModel bpmModel, List<ValidationError> errors) {
        // 获取流程启动者
        Map<String,String> starterMap = this.getProcessStarterForJsonXml(jsonXml);

        boolean isAtLeastOneExecutable = validateAtLeastOneExecutable(bpmModel,errors);
        if (isAtLeastOneExecutable) {
            for (Process process : bpmModel.getProcesses()) {

                // 判断流程启动者
                if(starterMap == null || (StringUtils.isBlank(starterMap.get("candidateUsers")) && StringUtils.isBlank(starterMap.get("candidateGroups")))){
                    addValidationError(errors, "流程模版的启动者不能为空", process, "流程模版中必须设置启动用户或启动的角色");
                }

                // 判断流程结束
                List<EndEvent> endEvents = process.findFlowElementsOfType(EndEvent.class);
                if (endEvents.size() == 0) {
                    addValidationError(errors, "没有定义结束事件", process, "流程模版中没有定义结束事件");
                }
                Iterator var1 = endEvents.iterator();
                while(var1.hasNext()) {
                    EndEvent endEvent = (EndEvent)var1.next();
                    if (endEvent.getIncomingFlows().size() == 0) {
                        addValidationError(errors, "结束事件中没有定义输入顺序流", process, "结束节点上没有连接输入顺序流");
                    }
                }

                // 判断开始
                List<StartEvent> startEvents = process.findFlowElementsOfType(StartEvent.class, false);
                Iterator var2 = startEvents.iterator();
                while(var2.hasNext()) {
                    StartEvent startEvent = (StartEvent)var2.next();
                    if (startEvent.getOutgoingFlows().size() == 0) {
                        addValidationError(errors, "开始事件中没有定义输出顺序流", process, "开始事件没有定义输出顺序流");
                    }
                }

                // 人工活动
                List<UserTask> userTasks = process.findFlowElementsOfType(UserTask.class);
                Iterator var3 = userTasks.iterator();
                List ids = new ArrayList();
                while(var3.hasNext()) {
                    UserTask userTask = (UserTask) var3.next();
                    if (!ids.contains(userTask.getId())) {
                        ids.add(userTask.getId());
                    } else {
                        addValidationError(errors, "人工活动没有定义输出顺序流", process, String.format("人工活动[%s]存在重复ID", new Object[] { userTask.getName() }));
                        break;
                    }
                    if ((userTask.getCandidateUsers().size() == 0) && (userTask.getCandidateGroups().size() == 0)) {
                        addValidationError(errors, "人工活动参与者不能为空", process, "流程模版中人工活动必须设置参与用户或参与的角色");
                    }
                    if ((userTask.getLoopCharacteristics() != null) && (userTask.getLoopCharacteristics().getLoopCardinality() != null) && (userTask.getLoopCharacteristics().getLoopCardinality().equals("role")) && ((userTask.getCandidateGroups() == null) || (userTask.getCandidateGroups().size() == 0))) {
                        addValidationError(errors, "人工活动执行者来源策略设置错误", process, String.format("多实例人工活动[%s]为按角色分配工作项，但是参与者没有设置角色", new Object[] { userTask.getName() }));
                    }
                    if ((userTask.getLoopCharacteristics() != null) && ((userTask.getAssignee().equals("!byStarter")) || (userTask.getAssignee().equals("!byLastUsertask")))) {
                        addValidationError(errors, "人工活动执行者来源策略设置错误", process, String.format("人工活动[%s]为多实例，执行者来源不能设置为启动者或上一步执行者", new Object[] { userTask.getName() }));
                    }
                    if (userTask.getIncomingFlows().size() == 0) {
                        addValidationError(errors, "人工活动没有定义输入顺序流", process, String.format("人工活动[%s]必须 定义输入流", new Object[] { userTask.getName() }));
                    }
                    if (userTask.getOutgoingFlows().size() == 0) {
                        addValidationError(errors, "人工活动没有定义输出顺序流", process, String.format("人工活动[%s]必须 定义输出流", new Object[] { userTask.getName() }));
                    }
                }
            }
        }
        return errors;
    }

    /**
     * 添加错误信息
     */
    private static void addValidationError(List<ValidationError> errors, String problem, Process process, String description) {
        ValidationError error = new ValidationError();
        if (process != null) {
            error.setProcessDefinitionId(process.getId());
            error.setProcessDefinitionName(process.getName());
        }
        error.setProblem(problem);
        error.setDefaultDescription(description);
        errors.add(error);
    }

    private static boolean validateAtLeastOneExecutable(BpmnModel bpmnModel, List<ValidationError> errors) {
        int nrOfExecutableDefinitions = 0;
        for (Process process : bpmnModel.getProcesses()) {
            if (process.isExecutable()) {
                nrOfExecutableDefinitions++;
            }
        }
        return nrOfExecutableDefinitions > 0;
    }
    /**
     * 查询模型列表
    */
    @Override
    public List<RepositoryModel> findModel(String catalogId){
        List<RepositoryModel> list = modelMapper.findModelByPage(catalogId);
        if(null!=list&&list.size()>0){
            for(int i=0;i<list.size();i++){
                RepositoryModel repositoryModel = JsonUtils.jsonToObject(list.get(i).getMetaInfo(),RepositoryModel.class);
                list.get(i).setDesc(repositoryModel.getDescription());
            }
        }
        return list;
    }

    /**
     * 更新模型
     */
    @Override
    public void updateModel(String modelId, String name, String desc){
        try {
            Model model = this.repositoryService.getModel(modelId);
            String info = model.getMetaInfo();
            RepositoryModel repositoryModel = JsonUtils.jsonToObject(info,RepositoryModel.class);
            ObjectNode modelJson = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());
            modelJson.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelJson.put(ModelDataJsonConstants.MODEL_REVISION, repositoryModel.getRevision());
            modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, desc);
            //modelJson.put("json", JsonUtils.toJson(irepositoryService.getProcessStarterForJsonXml(json)));//组装流程启动者，例：{"users":"1,2,3"},{"groups":"1,2,3"}
            modelJson.put("json","");
            model.setMetaInfo(modelJson.toString());
            model.setName(name);
            this.repositoryService.saveModel(model);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ActivitiException("Error saving model", e);
            }
    }

    /**
     * 删除流程定义
     */
    @Override
    public void deleteModel(String modelId) {
        repositoryService.deleteModel(modelId);
        catalogMapper.deleteCatalogModel(modelId);
    }

    /**
     * 通过taskId获取流程定义对象
     */
    @Override
    public ProcessDefinition getProcessDefinition(String taskId) {
        // 得到task
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//        // 通过task对象的pdid获取流程定义对象
//        ProcessDefinition pd = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
//        return pd;
        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }

    public List<Map<String, String>> getCandidate(String prcdId, String atvdId)
    {
//        ProcessDefinitionEntity pd = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
//                .getDeployedProcessDefinition(prcdId);
//
//        ActivityImpl ai = pd.findActivity(atvdId);
//        if (ai == null) {
//            throw new RuntimeException("活动模板[" + atvdId + "]不存在。");
//        }
//        TaskDefinition td = (TaskDefinition)ai.getProperty("taskDefinition");
        List candidate = new ArrayList();
//        Set cues = td.getCandidateUserIdExpressions();
//        for (Expression exp : cues) {
//            Map map = new HashMap();
//            map.put("id", exp.getExpressionText());
//            map.put("name", this.identity.getUserNameByUserId(exp.getExpressionText()));
//
//            map.put("fullname", this.identity.getFullNameByUserId(exp.getExpressionText()));
//
//            map.put("type", "User");
//            candidate.add(map);
//        }
//
//        cues = td.getCandidateGroupIdExpressions();
//        for (Iterator i$ = cues.iterator(); i$.hasNext(); ) { exp = (Expression)i$.next();
//            Map map = new HashMap();
//            map.put("id", exp.getExpressionText());
//            map.put("name", this.identity.getUserGroupNameByUserGroupId(exp.getExpressionText()));
//
//            map.put("type", "OrgRole");
//            candidate.add(map);
//            List useridnames = this.identity.getUserIdNamesByUserGroupId(exp.getExpressionText());
//
//            for (Map useridname : useridnames) {
//                Map mapu = new HashMap();
//                mapu.put("id", useridname.get("id"));
//                mapu.put("name", useridname.get("name"));
//
//                mapu.put("fullname", useridname.get("fullname"));
//                mapu.put("type", "User");
//                mapu.put("pid", exp.getExpressionText());
//                candidate.add(mapu);
//            }
//        }
//        Expression exp;
        return candidate;
    }

	@Override
	public List<IProcess> queryProcessDefListByPage() {
		return modelMapper.queryProcessDefListByPage();
	}

	@Override
	public void suspendProcessDef(String processdefId) {
		repositoryService.suspendProcessDefinitionById(processdefId);
	}

	@Override
	public void activateProcessDef(String processDefId) {
		repositoryService.activateProcessDefinitionById(processDefId);
	}
}
