package com.activiti.controller;

import com.activiti.base.entity.DelegateRule;
import com.activiti.base.entity.IProcess;
import com.activiti.base.entity.ITask;
import com.activiti.base.entity.ITaskInst;
import com.activiti.base.entity.ProcInst;
import com.activiti.base.entity.common.PageResult;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.IHistoryService;
import com.activiti.base.service.IRepositoryService;
import com.activiti.base.service.IRuntimeService;
import com.activiti.base.service.ITaskService;
import com.activiti.utils.JsonUtils;
import com.github.pagehelper.PageInfo;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端
 *
 **/
@Controller
@RequestMapping("client")
public class ClientController {
	private Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ITaskService taskService;

	@Autowired
	private IRepositoryService repositoryService;

	@Autowired
	IRuntimeService runtimeService;

	@Autowired
	IHistoryService historyService;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,String[] roleId) {
		if(roleId == null){
			roleId = new String[0];
		}
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		List<ITask> taskList = taskService.queryTask(userId, roleId);
		mav.addObject("taskList", taskList);
		List<IProcess> proceList = repositoryService.getProcessStartupAuth(userId, null);
		mav.addObject("proceList", proceList);
		mav.addObject("userId", userId);
		mav.setViewName("/client/index.jsp");
		return mav;
	}

	/**
	 * 发送任务
	 *
	 * @param taskId
	 * @return
	 */
	@RequestMapping("send/{taskId}")
	@ResponseBody
	public ResultMessage send(@PathVariable String taskId, String userId) {
		logger.debug("发送任务 : taskId = " + taskId);
		taskService.complete(taskId, userId);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 启动流程
	 *
	 * @param processKey
	 */
	@RequestMapping("start/{processKey}")
	@ResponseBody
	public ResultMessage startProcess(@PathVariable String processKey, String userId) {
		System.out.println("启动流程 : processKey = " + processKey);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, userId);
		System.out.println("pid:"+processInstance.getId());
		System.out.println("activitiId::"+processInstance.getActivityId());
		return new ResultMessage(0, "成功", processInstance.getProcessInstanceId());
	}

	/**
	 * 根据任务id回退到上一节点
	 *
	 * @param taskId
	 * @return
	 */
	@RequestMapping("back/{taskId}")
	@ResponseBody
	public ResultMessage back(@PathVariable String taskId, String userId) {
		logger.debug("根据任务id回退到上一节点: taskId = " + taskId);
		taskService.rollback(taskId, userId, null, null);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 中止流程
	 */
	@RequestMapping("deleteProcess/{procInstId}")
	@ResponseBody
	public ResultMessage deleteProcess(@PathVariable String procInstId) {
		logger.debug("中止流程: procInstId = " + procInstId);
		runtimeService.deleteProcessInstance(procInstId, "deleteProcess");
		return new ResultMessage(0, "成功");
	}

	/**
	 * 签收任务
	 *
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("claim/{taskId}/{userId}")
	public ResultMessage claim(@PathVariable String taskId, @PathVariable String userId) {
		logger.debug("签收任务: taskId = " + taskId);
		taskService.claim(taskId, userId);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 取消签收任务
	 *
	 * @param taskId
	 *            任务id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("unclaim/{taskId}")
	public ResultMessage unclaim(@PathVariable String taskId) {
		taskService.unclaim(taskId);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 查看流程图
	 */
	@RequestMapping(value = "/showCurrentImg/{procInstId}")
	public void showCurrentImg(@PathVariable String procInstId, HttpServletResponse response) throws Exception {
		logger.debug("查看流程图: procInstId = " + procInstId);
		historyService.showActivitiImage(procInstId, true,response);
	}

	/**
	 * 分页查询任务
	 */
	@RequestMapping("/queryTask")
	public ResultMessage queryTask(HttpServletRequest request, String[] roleId) {
		if(roleId == null){
			roleId = new String[0];
		}
		String type = request.getParameter("type");// 1:代办,2:已办,3:已归档,4:已撤销
		String userId = request.getParameter("userId");
		logger.debug("分页查询任务: type(1:代办,2:已办,3:已归档,4:已撤销) = " + type + ",roleId=" + roleId +",userId=" + userId);
		PageInfo<ITask> taskList = taskService.queryTaskByPage(userId, roleId, type);
		return new ResultMessage(0, "成功", taskList);
	}

	/**
	 * 分页查询可发起的流程
	 */
	@ResponseBody
	@RequestMapping("/queryStartupProcess")
	public PageResult queryStartupProcess(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<IProcess> proceList = repositoryService.getStartupProcess(userId);
		return new PageResult(0, "成功", proceList);
	}

	/**
	 * 查询已办任务(所有经手的)
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryHiTaskAll")
	public PageResult queryHiTaskAll(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<ITaskInst> taskList = taskService.queryHiTaskAll(userId);
		return new PageResult(0, "成功", taskList);
	}

	/**
	 * 查询已办任务(我发起的)
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryHiTaskStart")
	public PageResult queryHiTaskStartAll(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<ProcInst> taskList = taskService.queryHiTaskStartByPage(userId);
		return new PageResult(0, "成功", taskList);
	}
	
	/**
	 * 查询已办任务(我发起的)
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryHiTaskStart/{prco}")
	public PageResult queryHiTaskStart(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<ProcInst> taskList = taskService.queryHiTaskStartByPage(userId);
		return new PageResult(0, "成功", taskList);
	}

	/**
	 * 查询已办任务(已归档)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryHiTaskFiled")
	public ResultMessage queryHiTaskFiled(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		PageInfo<ITaskInst> taskList = taskService.queryHiTaskFiledByPage(userId);
		return new ResultMessage(0, "成功", taskList);
	}

	/**
	 * 查询已办任务(已撤销)
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryHiTaskCancel")
	public ResultMessage queryHiTaskCancel(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		PageInfo<ITaskInst> taskList = taskService.queryHiTaskCancelByPage(userId);
		return new ResultMessage(0, "成功", taskList);
	}

	/**
	 * 获取当前流程的下一个节点
	 * 
	 * @param procInstanceId
	 * @return
	 */
	@RequestMapping("getNextNode/{procInstanceId}")
	@ResponseBody
	public ResultMessage getNextNode(@PathVariable String procInstanceId) {
		logger.debug("获取当前流程的下一个节点: procInstanceId = " + procInstanceId);
		List<Map<String, Object>> list = taskService.getNextNode(procInstanceId);
		return new ResultMessage(0, "成功", list);
	}

	/**
	 * 分页获取所有流程定义集合
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("query/processDefList")
	public PageResult queryProcessDefList() {
		List<IProcess> processList = repositoryService.queryProcessDefListByPage();
		return new PageResult(0, "成功", processList);
	}

	/**
	 * 挂起流程定义
	 * 
	 * @param processDefId
	 *            流程定义id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("suspend/processDef/{processDefId}")
	public ResultMessage suspendProcessDef(@PathVariable String processDefId) {
		repositoryService.suspendProcessDef(processDefId);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 激活流程定义
	 * 
	 * @param processDefId
	 *            流程定义id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("activate/processDef/{processDefId}")
	public ResultMessage activateProcessDef(@PathVariable String processDefId) {
		repositoryService.activateProcessDef(processDefId);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 带业务id启动流程实例
	 * 
	 * @param processKey
	 *            流程定义key
	 * @param userId
	 *            用户id
	 * @param buzId
	 *            业务id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("start/{processKey}/{buzId}")
	public ResultMessage startProcessByBusinessId(@PathVariable String processKey, String userId,
			@PathVariable String buzId) {
		logger.debug("带业务id启动流程实例: processKey = " + processKey + ",buzId=" + buzId);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, userId, buzId);
		return new ResultMessage(0, "成功", processInstance.getProcessInstanceId());
	}

	/**
	 * 给流程实例设置业务id
	 * 
	 * @param procInstId
	 *            流程实例id
	 * @param buzId
	 *            业务id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setBusinessId")
	public ResultMessage setBusinessId(String procInstId, String buzId) {
		logger.debug("给流程实例设置业务id: procInstId = " + procInstId + ",buzId=" + buzId);
		runtimeService.setBusinessKey(procInstId, buzId);
		return new ResultMessage(0, "成功");
	}

	/**
	 * 查询待办任务
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryRuTask")
	public ResultMessage queryRuTask(HttpServletRequest request, String[] roleId) {
		if(roleId == null){
			roleId = new String[0];
		}
		String userId = request.getParameter("userId");
		String processDefKey = request.getParameter("processDefKey");
		logger.debug("查询待办任务: processDefKey = " + processDefKey + ",userId=" + userId + ",roleId="+roleId);
		List<ITask> taskList = taskService.queryRuTask(userId, roleId, processDefKey);
		return new ResultMessage(0, "成功", taskList);
	}

	/**
	 * 查询历史任务足迹信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryFootMarkInfo")
	public ResultMessage queryFootMarkInfo(HttpServletRequest request) {
		String procInstId = request.getParameter("procInstId");
		List<ITaskInst> taskList = taskService.queryFootMarkInfo(procInstId);
		return new ResultMessage(0, "成功", taskList);
	}
	
	/**
	 * 设置流程变量
	 * @param procInstId 流程实例id
	 * @param mapStr	变量mapStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setVariables")
	public ResultMessage setVariables(String procInstId, String mapStr){
		@SuppressWarnings("unchecked")
		Map<String,Object> variables = JsonUtils.jsonToObject(mapStr, HashMap.class);
		runtimeService.setVariables(procInstId, variables);
		return new ResultMessage(0, "成功");
	}
	
	/**
	 * 获取流程变量
	 * @param procInstId 流程实例id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getVariables")
	public ResultMessage getVariables(String procInstId){
		Map<String,Object> variables = runtimeService.getVariables(procInstId);
		return new ResultMessage(0, "成功", variables);
	}
	
	/**
	 * 签收流程实例的第一个任务
	 * @param procInstId 流程实例id
	 * @param userId 用户id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("claimProcessInstanceFristTask")
	public ResultMessage claimProcessInstanceFristTask(String procInstId, String userId){
		Task task = taskService.claimProcessInstanceFristTask(procInstId,userId);
		return new ResultMessage(0, "成功", task);
	}
	
	@ResponseBody
	@RequestMapping("queryDelegateRuleList")
	public ResultMessage queryDelegateRuleList(String processDefId,
			String activityId){
		List<DelegateRule> list = taskService.queryDelegateRuleList(processDefId, activityId);
		return new ResultMessage(0, "成功", list);
	}
	
}
