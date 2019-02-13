package com.activiti.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.activiti.base.entity.DelegateInfo;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.IRepositoryService;
import com.activiti.base.service.ITaskService;
import com.activiti.entity.common.BaseStatic;
import com.activiti.utils.HttpClientUtil;
import com.activiti.utils.JsonUtils;
import com.activiti.utils.ProcessDiagramGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Controller
@Scope("prototype")
@RequestMapping("/user")
public class Test {

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	ManagementService managementService;

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	HistoryService historyService;

	@Autowired
	ProcessEngine processEngine;

	@Autowired
	IRepositoryService iRepositoryService;
	
	@Autowired
	ITaskService itaskService;
	
	@Autowired
	FormService formService;

	@RequestMapping("/getUsers")
	@ResponseBody
	public ResultMessage getUsers() {
		String result = HttpClientUtil.sendHttpPost(BaseStatic.ACTIVITI_CLIENT_URL + "/user/getUsers");
		ResultMessage rm = JsonUtils.jsonToObject(result, ResultMessage.class);
		return new ResultMessage(0, "成功", rm.getData());
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView goTest(String name, String password) {
		ModelAndView mov = new ModelAndView();
		mov.setViewName("/index.jsp");
		return mov;
	}

	/**
	 * 创建模型
	 */
	@RequestMapping("create")
	public void create(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ProcessEngine processEngine =
			// ProcessEngines.getDefaultProcessEngine();
			//
			// RepositoryService repositoryService =
			// processEngine.getRepositoryService();

			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "lutiannan");
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			String description = "lutiannan---";
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName("lutiannan");
			modelData.setKey("12313123");

			// 保存模型
			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
			response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
		} catch (Exception e) {
			System.out.println("创建模型失败：");
		}
	}

	@RequestMapping("deploy/{modelId}")
	public String deploy(@PathVariable String modelId) {
		iRepositoryService.deploy(modelId);
		return "";
	}

	/*
	 * 查询流程定义
	 */
	public void findProcessDefinition() {
		List<ProcessDefinition> list = repositoryService// 与流程定义和部署对象相关的Service
				.createProcessDefinitionQuery()// 创建一个流程定义查询
				/* 指定查询条件,where条件 */
				// .deploymentId(deploymentId)//使用部署对象ID查询
				// .processDefinitionId(processDefinitionId)//使用流程定义ID查询
				// .processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
				// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

				/* 排序 */
				.orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
				// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

				.list();// 返回一个集合列表，封装流程定义
		// .singleResult();//返回唯一结果集
		// .count();//返回结果集数量
		// .listPage(firstResult, maxResults)//分页查询

		if (list != null && list.size() > 0) {
			for (ProcessDefinition processDefinition : list) {
				System.out.println("流程定义ID:" + processDefinition.getId());// 流程定义的key+版本+随机生成数
				System.out.println("流程定义名称:" + processDefinition.getName());// 对应HelloWorld.bpmn文件中的name属性值
				System.out.println("流程定义的key:" + processDefinition.getKey());// 对应HelloWorld.bpmn文件中的id属性值
				System.out.println("流程定义的版本:" + processDefinition.getVersion());// 当流程定义的key值相同的情况下，版本升级，默认从1开始
				System.out.println("资源名称bpmn文件:" + processDefinition.getResourceName());
				System.out.println("资源名称png文件:" + processDefinition.getDiagramResourceName());
				System.out.println("部署对象ID:" + processDefinition.getDeploymentId());
				System.out.println("################################");
			}
		}

	}
	
	@RequestMapping("start/{processKey}")
	public void startProcess(@PathVariable String processKey) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey);
		System.out.println("流程梳理所属流程定义id：" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例的id：" + processInstance.getProcessInstanceId());
		System.out.println("流程实例的执行id：" + processInstance.getId());
		System.out.println("流程当前的活动（结点）id：" + processInstance.getActivityId());
		System.out.println("业务标识：" + processInstance.getBusinessKey());
	}

	//@RequestMapping("queryTask/{user}")
	//public void queryTask(@PathVariable String user) {
	public void queryTask(String user) {
		String assignee = user;
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateUser(assignee);
		List<Task> list = taskQuery.list();
		for (Task task : list) {
			// 流程实例id
			String processInstanceId = task.getProcessInstanceId();
			// 根据流程实例id找到流程实例对象
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(processInstanceId).singleResult();
			// 从流程实例对象获取bussinesskey
			String businessKey = processInstance.getBusinessKey();
			// 根据businessKey查询业务系统，获取相关的业务信息
			System.out.println("流程实例id：" + task.getProcessInstanceId());
			System.out.println("任务id：" + task.getId());
			System.out.println("任务标识：" + task.getTaskDefinitionKey());
			System.out.println("任务负责人：" + task.getAssignee());
			System.out.println("任务名称：" + task.getName());
			System.out.println("任务创建时间：" + task.getCreateTime());
		}
	}

	@RequestMapping("send/{taskId}/{important}")
	public void send(@PathVariable String taskId, @PathVariable String important) {
		Map<String, Object> variable = new HashMap<String, Object>();
		if ("T".equals(important)) {
			variable.put("message", "重要");
		} else {
			variable.put("message", "不重要");
		}
		taskService.complete(taskId, variable);
	}

	/**
	 * 获取当前流程的下一个节点
	 * 
	 * @param procInstanceId
	 * @return
	 */
	@RequestMapping("getNextNode/{procInstanceId}")
	public void getNextNode(@PathVariable String procInstanceId) {
		// 1、首先是根据流程ID获取当前任务：
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstanceId).list();
		for (Task task : tasks) {
			// 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(task.getProcessDefinitionId());
			List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
			// 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
			String excId = task.getExecutionId();
			ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
					.singleResult();
			String activitiId = execution.getActivityId();
			// 4、然后循环activitiList
			// 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activitiId.equals(id)) {

					TaskDefinition td = (TaskDefinition) activityImpl.getProperty("taskDefinition");
					List candidate = new ArrayList();
					Set cues = td.getCandidateUserIdExpressions();

					System.out.println("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
					List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
					for (PvmTransition tr : outTransitions) {
						PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
						System.out.println("下一步任务任务：" + ac.getId() + "," + ac.getProperty("name"));
					}
					break;
				}
			}
		}
	}
	
	@RequestMapping("test1")
	public String test(){
		String deploymentId = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId("QJYSLC:8:08b5d0807ff544b5b1bf4ba1d0cc403b").singleResult().getDeploymentId();
		repositoryService.deleteDeployment(deploymentId);
		return "";
		
	}


	/**
	 * 打开流程图显示页面
	 **/
	@RequestMapping("openImage")
	public ModelAndView openActivitiProccessImagePage(String pProcessInstanceId) throws Exception {
		System.out.println("[开始]-打开流程图显示页面");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("processInstanceId", pProcessInstanceId);
		modelAndView.setViewName("/client/ActivitiProccessImagePage.jsp");
		System.out.println("[完成]-打开流程图显示页面");
		return modelAndView;
	}

	/**
	 * 获取流程图像，已执行节点和流程线高亮显示
	 */
	@RequestMapping("getActivitiProccessImage")
	public void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response) throws Exception {
		System.out.println("[开始]-获取流程图图像");
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			//  获取历史流程实例
			HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(pProcessInstanceId).singleResult();

			if (historicProcessInstance == null) {
				System.out.println("获取流程实例ID[" + pProcessInstanceId + "]对应的历史流程实例失败！");
			} else {
				// 获取流程定义
				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
						.getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

				// 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
				List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
						.processInstanceId(pProcessInstanceId).orderByHistoricActivityInstanceId().asc().list();

				// 已执行的节点ID集合
				List<String> executedActivityIdList = new ArrayList<String>();
				int index = 1;
				System.out.println("获取已经执行的节点ID");
				for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
					executedActivityIdList.add(activityInstance.getActivityId());
					System.out.println("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
					index++;
				}

				// 获取流程图图像字符流
				InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);

				response.setContentType("image/png");
				OutputStream os = response.getOutputStream();
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				imageStream.close();
			}
			System.out.println("[完成]-获取流程图图像");
		} catch (Exception e) {
			System.out.println("【异常】-获取流程图失败！" + e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping("testAuth")
	public ResultMessage testAuth(String userId, String[] roleId){
		List<DelegateInfo> delegateInfoList = itaskService.queryDelegateAuth(userId, roleId);
		return new ResultMessage(0, "查询成功", delegateInfoList);
	}

}
