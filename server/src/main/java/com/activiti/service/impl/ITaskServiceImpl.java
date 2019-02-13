package com.activiti.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.form.DefaultTaskFormHandler;
import org.activiti.engine.impl.form.FormPropertyHandler;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.activiti.base.entity.Activity;
import com.activiti.base.entity.DelegateInfo;
import com.activiti.base.entity.DelegateRule;
import com.activiti.base.entity.IProcess;
import com.activiti.base.entity.ITask;
import com.activiti.base.entity.ITaskInst;
import com.activiti.base.entity.ProcInst;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.ITaskService;
import com.activiti.config.HessianService;
import com.activiti.config.UUIDGenerator;
import com.activiti.entity.common.BaseStatic;
import com.activiti.mapper.ModelMapper;
import com.activiti.mapper.TaskMapper;
import com.activiti.utils.HttpClientUtil;
import com.activiti.utils.JsonUtils;
import com.github.pagehelper.PageInfo;

@HessianService
public class ITaskServiceImpl implements ITaskService {

	private final Logger log = LoggerFactory.getLogger(ITaskServiceImpl.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private FormService formService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void complete(String taskId, String userId) {
		this.complete(taskId, userId, new HashMap<String, Object>());
		// 添加足迹信息
		this.insertFootpoint(taskId, null, null);
	}

	@Override
	public void complete(String taskId, String userId, Map<String, Object> variables) {
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
        // 判断任务是否已经被签收
        if(task.getAssignee()==null){
            taskService.claim(taskId, userId);
        }
		taskService.complete(taskId, variables);
	}

	@Override
	public void claim(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	@Override
	public void unclaim(String taskId) {
		taskService.unclaim(taskId);
	}

	@Override
	public List<ITask> queryTask(String userId, String[] roleId) {
		return taskMapper.queryTask(userId, roleId);
	}

	@Override
	public PageInfo<ITask> queryTaskByPage(String userId, String[] roleId, String type) {
		List<ITask> list = null;
		// 1:代办,2:已办,3:已归档,4:已撤销
		if ("1".equals(type)) {
			list = taskMapper.queryTaskByPage(userId, roleId);
		}
		PageInfo<ITask> result = new PageInfo<>(list);
		return result;
	}

	@Override
	public void rollback(String taskId, String userId, String result, String resultDesc) {
		rollbackOrClaim(taskId,userId,result,resultDesc,false);
	}

	/**
	 * 回退（是否需要签收）
	 * @param isClaim
	 */
	private void rollbackOrClaim(String taskId, String userId, String result, String resultDesc,boolean isClaim) {
		String prevAssignee = "";//上一步签收人
		String prevActivityId = "";//上一步签收人
		String processInstId = "";//流程实例id
		String processDefId = "";//流程定义id
		Map<String, Object> variables;
		// 取得当前任务
		HistoricTaskInstance currTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		// 取得流程实例
		ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(currTask.getProcessInstanceId()).singleResult();
		if (instance == null) {
			// 流程结束
		}

		if(isClaim){
			processInstId = instance.getProcessInstanceId();
			processDefId = instance.getProcessDefinitionId();
			//获取上一步签收人
			List<ITaskInst> tasks = taskMapper.queryFootMarkInfo(instance.getProcessInstanceId());
			if(tasks != null && tasks.size() >= 1){
				// 获取上一步签收人
				prevAssignee = tasks.get(1).getAssignee();
				prevActivityId = tasks.get(1).getActivityId();
			}
		}

		variables = instance.getProcessVariables();
		// 取得流程定义
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (repositoryService
				.getProcessDefinition(currTask.getProcessDefinitionId()));
		if (definition == null) {
			// log.error("流程定义未找到");
			return;
		}
		// 取得上一步活动
		ActivityImpl currActivity = ((ProcessDefinitionImpl) definition).findActivity(currTask.getTaskDefinitionKey());
		List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();
		// 清除当前活动的出口
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		// 建立新出口
		List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
		String code = taskMapper.queryLastActivityCode(instance.getId());
		List<ActivityImpl> activitiList = definition.getActivities();
		for(ActivityImpl ai : activitiList){
			if(code.equals(ai.getId())){
				TransitionImpl newTransition = currActivity.createOutgoingTransition();
				newTransition.setDestination(ai);
				newTransitions.add(newTransition);
			}
		}
		// 完成任务
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(instance.getId())
				.taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
		for (Task task : tasks) {
			this.complete(task.getId(), userId, variables);
			// historyService.deleteHistoricTaskInstance(task.getId());
		}
		// 恢复方向
		for (TransitionImpl transitionImpl : newTransitions) {
			currActivity.getOutgoingTransitions().remove(transitionImpl);
		}
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}

		if(isClaim){
			// 用之前的处理人签收当前任务
			List<Task> currTasks = taskService.createTaskQuery().processInstanceId(processInstId).taskDefinitionKey(prevActivityId).list();
			for (Task task : currTasks) {
				taskService.claim(task.getId(),prevAssignee);
			}
		}

		// 添加足迹信息
		this.insertFootpoint(taskId, result, resultDesc);
	}

	@Override
	public void rollbackAndClaim(String taskId, String userId, String result, String resultDesc) {
		rollbackOrClaim(taskId, userId, result, resultDesc,true);
	}

	/**
	 * 插入足迹信息
	 */
	@Override
	public int insertFootpoint(String taskId, String result, String resultDesc) {
		// 获取此次请求的request对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		result = result == null ? request.getParameter("result") : result;
		resultDesc = resultDesc == null ? request.getParameter("resultDesc") : resultDesc;
		return taskMapper.insertFootpoint(taskId, result, resultDesc);
	}

	/**
	 * 查询已办任务(所有我经手的)
	 */
	@Override
	public List<ITaskInst> queryHiTaskAll(String userId) {
		List<ITaskInst> list = taskMapper.queryHiTaskAll(userId);
		if(list != null && list.size() > 0){
			for (ITaskInst task : list) {
				task.setFormKey(formService.getStartFormKey(task.getProcessDefId()));
			}
		}
		return list;
	}

	/**
	 * 查询已办任务(我发起的)
	 */
	@Override
	public List<ProcInst> queryHiTaskStartByPage(String userId) {
		List<ProcInst> list = taskMapper.queryHiTaskStartByPage(userId);
		if(list != null && list.size() > 0){
			for(ProcInst p : list){
				p.setFormKey(formService.getStartFormKey(p.getProcessDefId()));
			}
		}
		return list;
	}

	/**
	 * 查询已办任务(已归档)
	 */
	@Override
	public PageInfo<ITaskInst> queryHiTaskFiledByPage(String userId) {
		List<ITaskInst> list = taskMapper.queryHiTaskFiledByPage(userId);
		PageInfo<ITaskInst> result = new PageInfo<>(list);
		return result;
	}

	/**
	 * 查询已办任务(已撤销)
	 */
	@Override
	public PageInfo<ITaskInst> queryHiTaskCancelByPage(String userId) {
		List<ITaskInst> list = taskMapper.queryHiTaskCancelByPage(userId);
		PageInfo<ITaskInst> result = new PageInfo<>(list);
		return result;
	}

	@Override
	public List<Task> getTaskByProciid(String prociid) {
		// 根据流程ID获取当前任务：
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(prociid).list();
		return tasks;
	}

	@Override
	public List<Map<String, Object>> getNextNode(String procInstanceId) {
		List<Map<String, Object>> nextNodes = new ArrayList<>();
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
						Map<String, Object> nextNodeMap = new HashMap<>();
						nextNodeMap.put("id", ac.getId());
						nextNodeMap.put("name", ac.getProperty("name"));
						nextNodes.add(nextNodeMap);
					}
					break;
				}
			}
		}
		return nextNodes;
	}

	/**
	 * 根据用户id和角色id和流程id查询待办任务
	 *
	 * @param userId
	 *            用户id
	 * @param roleId
	 *            角色id
	 * @param processDefKey
	 *            流程id
	 * @return
	 */
	@Override
	public List<ITask> queryRuTask(String userId, String[] roleId, String processDefKey) {
		List<Map<String,String>> userRoles = null;
		// 获取当前用户的被委托人ids
		List<String> assigneeIds = taskMapper.getAssigneeByUserId(userId);
		if(assigneeIds != null && assigneeIds.size() > 0){
			// 根据用户id，获取用户的角色
			Map<String,String> clientParam = new HashMap<>();
			clientParam.put("userIds", StringUtils.join(assigneeIds,","));
			String result = HttpClientUtil.sendHttpPost(BaseStatic.ACTIVITI_CLIENT_URL + BaseStatic.ACTIVITI_CLIENT_USER_ALL_ROLE,clientParam);
			ResultMessage rm = JsonUtils.jsonToObject(result, ResultMessage.class);
			userRoles = JsonUtils.jsonToList(JsonUtils.objectToJson(rm.getData()),Map.class);
		}
		
		List<DelegateInfo> ruleList = taskMapper.queryDelegateUserInfo(userId);

		Map<String,Object> params = new HashMap<>();
		params.put("userId",userId);
		params.put("roleId",roleId);
		params.put("processDefKey",processDefKey);
		params.put("userRoles",userRoles);
		params.put("ruleList", ruleList);

		return taskMapper.queryRuTask(params);
	}

	/**
	 * 查询历史任务的足迹信息
	 */
	@Override
	public List<ITaskInst> queryFootMarkInfo(String procInstId) {
		List<ITaskInst> list = taskMapper.queryFootMarkInfo(procInstId);
		return list;
	}

	@Override
	public Task claimProcessInstanceFristTask(String procInstId, String userId) {
		if(procInstId == null){
			return null;
		}
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInstId).list();
		if(taskList != null && taskList.size() > 0){
			this.claim(taskList.get(0).getId(), userId);
			return taskList.get(0);
		}
		return null;
	}

	@Override
	public List<DelegateInfo> queryDelegateAuth(String userId, String[] roleId) {
		if(roleId == null){
			roleId = new String[0];
		}
		List<DelegateInfo> delegateInfoList = new ArrayList<>();
		List<IProcess> processList = modelMapper.queryProcessDefUsedList();
		for(IProcess process :processList){
			DelegateInfo delegateInfo = null;
			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(process.getId());
			List<ActivityImpl> activitiList = def.getActivities();
			List<Activity> activityList = new ArrayList<Activity>();
			for(ActivityImpl ai : activitiList){
				if("userTask".equals(ai.getProperty("type"))){
					TaskDefinition td = (TaskDefinition) ai.getProperty("taskDefinition");
					Set<Expression> ids = td.getCandidateUserIdExpressions();
					List<String> userIds = new ArrayList<>(Arrays.asList(roleId));
					userIds.add(userId);
					if(isContain(ids,userIds)){
						if(delegateInfo == null){
							delegateInfo = new DelegateInfo();
							delegateInfo.setProcessName(process.getName());
							delegateInfo.setProcessDefId(process.getId());
						}
						Activity a = new Activity();
						a.setActivityId(ai.getId());
						a.setActivityName((String) ai.getProperty("name"));
						activityList.add(a);
					}
				}
			}
			if(delegateInfo != null){
				delegateInfo.setActivityList(activityList);
				delegateInfoList.add(delegateInfo);
			}
		}
		return delegateInfoList;
	}

	private boolean isContain(Set<Expression> idSet, List<String> ids) {
		Iterator<Expression> it = idSet.iterator();
		while(it.hasNext()){
			Expression e = it.next();
			String id = e.getExpressionText();
			for(String s : ids){
				if(id.equals(s)){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void delegateComplate(String delegateId, String taskId, String userId, String assignee,
			Map<String, Object> variables,String result, String resultDesc) {
		taskMapper.complateDelegate(UUIDGenerator.UUID(),delegateId,taskId,userId,assignee);
		if(variables == null){
			variables = new HashMap<String, Object>();
		}
		this.complete(taskId, assignee, variables);
		this.insertFootpoint(taskId, result, resultDesc);
	}

	@Override
	public DelegateInfo addDelegate(DelegateInfo delegateInfo) {
		delegateInfo.setDelegateInfoId(UUIDGenerator.UUID());
		taskMapper.addDelegate(delegateInfo);
		return delegateInfo;
	}

	@Override
	public List<DelegateInfo> queryDelegateInfoList(String userId) {
		return taskMapper.queryDelegateInfoList(userId);
	}

	@Override
	public void deleteDelegate(String delegateId) {
		taskMapper.deleteDelegate(delegateId);
	}

	@Override
	public void delegateRollback(String delegateId, String taskId,
			String userId, String assignee, Map<String, Object> variables,
			String result, String resultDesc) {
		taskMapper.complateDelegate(UUIDGenerator.UUID(),delegateId,taskId,userId,assignee);
		if(variables == null){
			variables = new HashMap<String, Object>();
		}
		this.rollbackOrClaim(taskId, userId, result, resultDesc,false);
	}

	@Override
	public void delegateRollbackAndClaim(String delegateId, String taskId, String userId, String assignee, Map<String, Object> variables, String result, String resultDesc) {
		taskMapper.complateDelegate(UUIDGenerator.UUID(),delegateId,taskId,userId,assignee);
		if(variables == null){
			variables = new HashMap<String, Object>();
		}
		this.rollbackOrClaim(taskId, userId, result, resultDesc,true);
	}

	@Override
	public List<DelegateRule> queryDelegateRuleList(String processDefId,
			String activityId) {
		List<DelegateRule> ruleList = new ArrayList<DelegateRule>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefId);
		List<ActivityImpl> activitiList = def.getActivities();
		for(ActivityImpl ai : activitiList){
			if(ai.getId().equals(activityId)){
				TaskDefinition td = (TaskDefinition) ai.getProperty("taskDefinition");
				DefaultTaskFormHandler form = (DefaultTaskFormHandler) td.getTaskFormHandler();
				List<FormPropertyHandler> formList =form.getFormPropertyHandlers();
				for(FormPropertyHandler fh : formList){
					DelegateRule rule = new DelegateRule();
					rule.setRuleKey(fh.getId());
					rule.setRuleName(fh.getName());
					rule.setRuleType(fh.getType().getName());
					ruleList.add(rule);
				}
			}
		}
		return ruleList;
	}

	@Override
	public void addDelegateRule(List<DelegateRule> delegateRuleList) {
		// TODO Auto-generated method stub
		if(delegateRuleList != null & delegateRuleList.size() > 0){
			for (DelegateRule delegateRule : delegateRuleList) {
				delegateRule.setRuleId(UUIDGenerator.UUID());
			}
			taskMapper.addDelegateRule(delegateRuleList);
		}
	}

	@Override
	public void deleteDelegateRule(String delegateId) {
		taskMapper.deleteDelegateRule(delegateId);
	}

	@Override
	public List<DelegateRule> queryHaveDelegateRuleList(String delegateId) {
		List<DelegateRule> ruleList = taskMapper.queryHaveDelegateRuleList(delegateId);
		return ruleList;
	}

	@Override
	public List<Task> queryTask(String assignee) {
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
		return list;
	}
	
	public Task createTaskQuery(String assignee, String prciId){
		 Task task = taskService.createTaskQuery()
				.processInstanceId(prciId)//使用流程实例ID
				.taskAssignee(assignee)//任务办理人
				.singleResult();
		 return task;
	}

}
