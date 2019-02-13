package com.tqy.cams.service.impl;

import com.activiti.base.entity.DelegateInfo;
import com.activiti.base.entity.DelegateRule;
import com.activiti.base.entity.ITask;
import com.activiti.base.entity.Node;
import com.activiti.base.entity.common.PageResult;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.ITaskService;
import com.activiti.base.util.HessianServiceFactory;
import com.tqy.cams.bean.*;
import com.tqy.cams.bean.common.BaseStatic;
import com.tqy.cams.dao.RzblMapper;
import com.tqy.cams.dao.UserMapper;
import com.tqy.cams.service.RzblService;
import com.tqy.cams.utils.HttpClientUtil;
import com.tqy.cams.utils.JsonUtils;
import com.tqy.cams.utils.StringUtil;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 认证办理
 **/
@Service
public class RzblServiceImpl implements RzblService {
    private final Logger logger = LoggerFactory.getLogger(RzblServiceImpl.class);

    private static ITaskService taskService = null;
    static {
        taskService = HessianServiceFactory.getService(ITaskService.class,BaseStatic.ACTIVITI_SERVER_URL);
    }

    @Autowired
    private RzblMapper rzMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public PageResult queryMyTask(String userId, String processDefKey) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", StringUtils.join(userMapper.getUserRoles(userId),","));
        params.put("processDefKey", processDefKey);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/queryRuTask",params);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            List<ITask> tasks = JsonUtils.jsonToList(JsonUtils.objectToJson(result.getData()),ITask.class);
            return new PageResult(BaseStatic.SUCCESS_CODE,"成功",tasks);
        }
        return new PageResult(BaseStatic.ERROR_CODE,"失败");
    }

    @Override
    public PageResult queryStartupProcess(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        PageResult result =  HttpClientUtil.handleActivitiPageHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/queryStartupProcess",params);
        return result;
    }

    @Override
    public ResultMessage startProcess(String userId, String processDefKey, String buzId) {
        String url = BaseStatic.ACTIVITI_SERVER_URL + "/client/start/" + processDefKey;
        if(!StringUtil.isNullOrBlank(buzId)){
            url = BaseStatic.ACTIVITI_SERVER_URL + "/client/start/" + processDefKey + "/" + buzId;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(url,params);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            String prciId = result.getData().toString();
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("prciId",prciId);
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",resultMap);
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }

    @Override
    public ResultMessage claimTask(String taskId, String userId) {
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/claim/" + taskId + "/" + userId,null);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }

    @Override
    public ResultMessage unclaimTask(String taskId) {
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/unclaim/" + taskId,null);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }

    @Override
    public ResultMessage sendTask(String taskId, String userId, String resultType, String resultDesc) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("result", resultType);
        params.put("resultDesc", resultDesc);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/send/" + taskId,params);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }

    @Override
    public ResultMessage backTask(String taskId, String userId, String resultType, String resultDesc) {
        taskService.rollbackAndClaim(taskId,userId,resultType,resultDesc);
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
    }

    @Override
    public ResultMessage setBusinessId(String prciId, String buzId) {
        Map<String, String> params = new HashMap<>();
        params.put("procInstId", prciId);
        params.put("buzId", buzId);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/setBusinessId",params);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }

    @Override
    public ResultMessage setVariables(String prciId) {
        Map<String, String> procProMap = new HashMap<>();
        procProMap.put("procInstId",prciId);
        Map<String, Object> variables = new HashMap<>();
        procProMap.put("mapStr", JsonUtils.objectToJson(variables));
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/setVariables",procProMap);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }
    
    @Override
    public ResultMessage getVariables(String prciId) {
        Map<String, String> procProMap = new HashMap<>();
        procProMap.put("procInstId",prciId);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/getVariables",procProMap);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",result.getData());
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
    }
    
    @Override
    public ResultMessage upload(String applicationId,MultipartFile file, String dirPath) {
    	//获取上传文件的原始名称
        String originalFilename = file.getOriginalFilename();
        File filePath = new File(dirPath);
        //如果保存文件的地址不存在，就先创建目录
        if(!filePath.exists()) {
            filePath.mkdirs();
        }
        try {
            //使用MultipartFile接口的方法完成文件上传到指定位置
            file.transferTo(new File(dirPath + originalFilename));
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
        }catch(Exception e) {
           logger.error("上传文件出错："+e.getMessage());
           return new ResultMessage(BaseStatic.ERROR_CODE,"失败");
        }
    }
    
    @Override
    public ResultMessage updateRzApplication(RzApplication rz,String userId) {
    	//启动流程实例
    	ResultMessage mes = startProcess(userId, "rwbl", "");
    	Map<String, String> map = JsonUtils.jsonToObject(JsonUtils.objectToJson(mes.getData()), HashMap.class);
    	String prciId = map.get("prciId");
    	//String prciId = "361c405021fb4ca09d1f4a384dd9c677";
        // 新增
        if(StringUtil.isNullOrBlank(rz.getId())){
            rz.setId(UUID.randomUUID().toString());
            rzMapper.saveRzApplication(rz);
        }else{
            rzMapper.updateRzApplication(rz);
        }
        logger.info("保存认证申请表单数据成功");
        //保存业务与流程关系数据
        this.setBusinessId(prciId, rz.getId());
        logger.info("保存认证申请与流程关系数据成功");
        
        Task task = taskService.createTaskQuery(rz.getApplicationUserName(), prciId);
        // 设置流程变量
        Map<String, String> procProMap = new HashMap<>();
        procProMap.put("procInstId",prciId);
        procProMap.put("taskId", task.getId());
        procProMap.put("userId", userId);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/setVariables",procProMap);
        logger.info("设置流程变量成功");

         // 流转任务
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
	        //发送流程
	        this.sendTask(task.getId(),userId, "发送申请", null);
	        logger.info("发送认证申请成功");
        }
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
    }
    
    @Override
    public ResultMessage queryAllRzApp(String pageNo, String pageSize, String systemName, String managerDept, String developDept) {
    	int start = 0;
    	int end = 0;
    	if(!StringUtil.isNullOrBlank(pageNo) && !StringUtil.isNullOrBlank(pageSize)){
    		start = Integer.parseInt(pageNo)-1;
    		end = Integer.parseInt(pageNo) * Integer.parseInt(pageSize);
    	}
    	int totalSize = rzMapper.queryCount(systemName,managerDept,developDept);
    	List<RzApplication> info = rzMapper.queryRzAppByCondition(start+"", end+"", systemName,managerDept,developDept);
    	for(RzApplication rz : info) {
    		if(StringUtil.isNullOrBlank(rz.getResult())) {
    			rz.setResult("待审核");
    		}else if(rz.getResult().equals("通过")) {
    			rz.setResult("已入网");
    		}
    	}
    	PageInfo pages = new PageInfo(start+1, totalSize, Integer.parseInt(pageSize), info);
    	return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功", pages);
    }

    @Override
    public ResultMessage getRzApplication(String id) {
    	RzApplication rz = rzMapper.getRzApplicationById(id);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("rz",rz);
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",resultMap);
    }
    
    @Override
    public ResultMessage updateCheckRzApplication(RzCheckRz check,String userId) {
    	ResultMessage mes = startProcess(userId, "rwbl", "");
    	Map<String, String> map = JsonUtils.jsonToObject(JsonUtils.objectToJson(mes.getData()), HashMap.class);
    	String prciId = map.get("prciId");
    	RzCheckRz checkRz = rzMapper.getCheckRzApplicationById(check.getApplicationId());
    	if(StringUtil.isNullOrBlank(checkRz.getCheckUserName())) {
    		rzMapper.saveCheckRzApplication(check);
    	}else {
    		rzMapper.updateCheckRzApplication(check);
    	}
    	logger.info("保存认证审核表单数据成功");
        //保存业务与流程关系数据
        this.setBusinessId(prciId, check.getApplicationId());
        logger.info("保存认证审核与流程关系数据成功");

        Task task = taskService.createTaskQuery(checkRz.getCheckUserName(), prciId);
        // 设置流程变量
        Map<String, String> procProMap = new HashMap<>();
        procProMap.put("procInstId",prciId);
        procProMap.put("taskId", task.getId());
        procProMap.put("userId", userId);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/setVariables",procProMap);
        logger.info("设置流程变量成功");
        // 流转任务
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            //发送流程
            this.sendTask(task.getId(), userId, "发送审核", null);
            logger.info("发送认证审核成功");
        }
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
    }
    
    @Override
    public ResultMessage getCheckRzApplication(String applicationId) {
        Map<String,Object> resultMap = new HashMap<>();
        RzCheckRz checkRz = rzMapper.getCheckRzApplicationById(applicationId);
        resultMap.put("checkRz",checkRz);
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",resultMap);
    }
    
    @Override
    public PageResult myApplication(String userId, String processDefKey) {
        String url = BaseStatic.ACTIVITI_SERVER_URL + "/client/queryHiTaskStart";
        if(!StringUtil.isNullOrBlank(processDefKey)){
            url = url + "/" + processDefKey;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        PageResult result = HttpClientUtil.handleActivitiPageHttpPost(url,params);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            Map<String, Class> classMap = new HashMap<String, Class>();
            classMap.put("nodes", Node.class);
            List<MyProcInst> procInsts = JsonUtils.jsonToList(JsonUtils.objectToJson(result.getData()),MyProcInst.class,classMap);
            if(procInsts != null && procInsts.size() > 0){
                Map<String, Object> map = new HashMap<>();
                map.put("list",procInsts);
                List<MyProcInst> myList = rzMapper.getMyApplicationByPage(map);
                if(myList != null && myList.size() > 0){
                    for (MyProcInst procInst : myList) {
                        if(procInst.getEndTime() == null){
                            // 处理当前节点信息
                            List<Node> nodes = procInsts.get(procInst.getIndex()).getNodes();
                            String name = "";
                            for (Node node : nodes) {
                                name += (node.getName() + "|");
                            }
                            procInst.setDeleteReason("[进行中]:" + name.substring(0,name.length()-1 < 0 ? 0 : name.length()-1));
                            procInst.setStatus("now");
                        }else{
                            if("deleteProcess".equals(procInst.getDeleteReason())){
                                procInst.setDeleteReason("[用户撤销]");
                                procInst.setStatus("delete");
                            }else{
                                procInst.setDeleteReason("[已完成]");
                                procInst.setStatus("complete");
                            }
                        }
                    }
                }
                logger.info("###myApplication: " + JsonUtils.objectToJson(myList));
                return new PageResult(BaseStatic.SUCCESS_CODE,"成功",myList);
            }
        }
        return result;
    }

    @Override
    public PageResult queryHiTaskAll(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        PageResult result = HttpClientUtil.handleActivitiPageHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/queryHiTaskAll",params);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            List<MyTaskInst> taskInsts = JsonUtils.jsonToList(JsonUtils.objectToJson(result.getData()),MyTaskInst.class);
            if(taskInsts != null && taskInsts.size() > 0){
                Map<String, Object> map = new HashMap<>();
                map.put("list",taskInsts);
                List<MyTaskInst> list = rzMapper.queryHiTaskAllByPage(map);
                if(list != null && list.size() > 0){
                    for (int i = 0; i < list.size(); i++) {
                        if(taskInsts.get(i).getDelegates() != null){
                            List<DelegateInfo> temp = new ArrayList<>();
                            for (int j = 0; j < 2; j++) {
                                DelegateInfo delegateInfo = new DelegateInfo();
                                delegateInfo.setAttorney("attorney" + i);
                                delegateInfo.setStatus("进行中");
                                temp.add(delegateInfo);
                            }
                            list.get(i).setDelegates(temp);
//                            list.get(i).setDelegates(taskInsts.get(i).getDelegates());
                        }
                    }
                }
                return new PageResult(BaseStatic.SUCCESS_CODE,"成功",list);
            }
        }
        return result;
    }

    @Override
    public void showImg(String prciId, String procDefId, HttpServletResponse response) {

        try {
            response.sendRedirect(BaseStatic.ACTIVITI_SERVER_URL + "/client/showCurrentImg/" + prciId);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Map<String, String> params = new HashMap<>();
//        params.put("procDefId",procDefId);
//        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/showImg/" + prciId,params);
//        if(result.getCode() == BaseStatic.SUCCESS_CODE){
//            byte[] buff = new byte[0];
//            try {
//                buff = result.getData().toString().getBytes("UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            InputStream imageStream = InputStreamUtils.byte2Input(buff);
//            // 输出资源内容到相应对象
//            byte[] b = new byte[1024];
//            int len;
//            try{
//                while ((len = imageStream.read(b, 0, 1024)) != -1) {
//                    response.getOutputStream().write(b, 0, len);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public ResultMessage deleteProcess(String prciId) {
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/deleteProcess/" + prciId,null);
        return result;
    }

    @Override
    public ResultMessage getAuth(String userId) {
        List<DelegateInfo> list = taskService.queryDelegateAuth(userId,userMapper.getUserRoles(userId));
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",list);
    }

    @Override
    public ResultMessage addDelegate(String jsonStr) {
        DelegateInfo delegateInfo = JsonUtils.jsonToObject(jsonStr,DelegateInfo.class);
        DelegateInfo info = taskService.addDelegate(delegateInfo);
        delegateInfo.setDelegateInfoId(info.getDelegateInfoId());
        taskService.deleteDelegateRule(delegateInfo.getDelegateInfoId());

        if(delegateInfo.getRuleList() != null && delegateInfo.getRuleList().size() > 0){
            for (DelegateRule delegateRule : delegateInfo.getRuleList()) {
                delegateRule.setDelegateId(info.getDelegateInfoId());
            }
            taskService.addDelegateRule(delegateInfo.getRuleList());
        }
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"新增委托成功");
    }

    @Override
    public PageResult queryDelegateInfoList(String userId) {
        List<DelegateInfo> list = taskService.queryDelegateInfoList(userId);
        if(list != null && list.size() > 0){
        	return new PageResult(BaseStatic.SUCCESS_CODE,"查询委托列表成功",rzMapper.queryDelegateInfoListByPage(list));
        }
        return new PageResult(BaseStatic.SUCCESS_CODE,"查询委托列表成功",new ArrayList<DelegateInfo>());
        //List<DelegateInfo> list1 = rzMapper.queryDelegateInfoListByPage(list);
    }

	@Override
	public ResultMessage sendTaskDelegate(String taskId, String userId,
			String result, String resultDesc, String delegater,
			String delegateId) {
		taskService.delegateComplate(delegateId, taskId, userId, delegater, new HashMap<String, Object>(), result, resultDesc);
		return new ResultMessage(BaseStatic.SUCCESS_CODE,"发送任务成功");
	}

	@Override
	public void deleteDelegate(String id) {
		taskService.deleteDelegate(id);
	}

	@Override
	public ResultMessage backTaskDelegate(String taskId, String userId,
			String result, String resultDesc, String delegater,
			String delegateId) {
		taskService.delegateRollbackAndClaim(delegateId, taskId, userId, delegater, new HashMap<String, Object>(), result, resultDesc);
		return new ResultMessage(BaseStatic.SUCCESS_CODE,"回退任务成功");
	}

    @Override
    public ResultMessage queryDelegateList(String processDefId, String activityId) {
        List<DelegateRule> list = taskService.queryDelegateRuleList(processDefId,activityId);
        if(list == null || list.size() == 0){
            list.add(new DelegateRule());
            list.add(new DelegateRule());
        }
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",list);
    }

    @Override
    public ResultMessage addDelegateRule(List<DelegateRule> rules) {
        taskService.addDelegateRule(rules);
        return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功");
    }

    @Override
    public ResultMessage getFootpoint(String prciId) {
        // 获取足迹信息
        Map<String, String> procProMap = new HashMap<>();
        procProMap.put("procInstId",prciId);
        ResultMessage result = HttpClientUtil.handleActivitiHttpPost(BaseStatic.ACTIVITI_SERVER_URL + "/client/queryFootMarkInfo",procProMap);
        if(result.getCode() == BaseStatic.SUCCESS_CODE){
            List<MyTaskInst> list = null;
            List<MyTaskInst> taskInsts = JsonUtils.jsonToList(JsonUtils.objectToJson(result.getData()),MyTaskInst.class);
            if(taskInsts != null && taskInsts.size() > 0){
                Map<String, Object> map = new HashMap<>();
                map.put("list",taskInsts);
                list = rzMapper.queryHiTaskAllByPage(map);
            }
            return new PageResult(BaseStatic.SUCCESS_CODE,"成功",list);
        }
        return result;
    }

}
