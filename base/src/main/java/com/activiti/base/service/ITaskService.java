package com.activiti.base.service;

import com.activiti.base.entity.*;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ITaskService {
	
	/**
	 * 查询用户下的所有任务
	 */
	List<Task> queryTask(String assignee);

	/**
	 * 根据用户名和流程ID获取任务
	 * @param assignee
	 * @param prciId
	 * @return
	 */
	Task createTaskQuery(String assignee, String prciId);
	
	/**
	 * 根据任务id完成任务
	 *
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            用户id
	 */
	void complete(String taskId, String userId);

	/**
	 * 根据任务id和流程变量map完成任务并且设置流程变量
	 *
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            用户id
	 * @param variables
	 *            流程变量map
	 */
	void complete(String taskId, String userId, Map<String, Object> variables);

	/**
	 * 根据任务id和用户id签收任务
	 *
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            用户id
	 */
	void claim(String taskId, String userId);

	/**
	 * 根据任务id取消签收
	 *
	 * @param taskId
	 */
	void unclaim(String taskId);

	/**
	 * 根据用户id和角色id查询任务
	 *
	 * @param userId
	 *            用户id
	 * @param roleId
	 *            角色id
	 * @return
	 */
	List<ITask> queryTask(String userId, String[] roleId);

	/**
	 * 分页查询用户任务
	 *
	 * @param userId
	 *            用户id
	 * @param roleId
	 *            角色id
	 * @param type
	 *            1:代办,2:已办,3:已归档,4:已撤销
	 * @return
	 */
	PageInfo<ITask> queryTaskByPage(String userId, String[] roleId, String type);

	/**
	 * 根据任务id回退到上一节点
	 * @param taskId	任务id
	 * @param userId	执行人id
	 * @param result	执行结果
	 * @param resultDesc 结果描述
	 */
	void rollback(String taskId, String userId, String result, String resultDesc);

	/**
	 * 根据任务id回退到上一节点，并且让之前处理人签收任务
	 * @param taskId	任务id
	 * @param userId	执行人id
	 * @param result	执行结果
	 * @param resultDesc 结果描述
	 */
	void rollbackAndClaim(String taskId, String userId, String result, String resultDesc);

	/**
	 * 插入足迹信息
	 * @param taskId	任务id
	 * @param result	执行结果
	 * @param resultDesc 结果描述
	 * @return
	 */
	int insertFootpoint(String taskId, String result, String resultDesc);

	/**
	 * 查询已办任务(所有我经手的)
	 *
	 * @param userId
	 * @return
	 */
	List<ITaskInst> queryHiTaskAll(String userId);

	/**
	 * 查询已办任务(我发起的)
	 *
	 * @param userId
	 * @return
	 */
	List<ProcInst> queryHiTaskStartByPage(String userId);

	/**
	 * 查询已办任务(已归档)
	 *
	 * @param userId
	 * @return
	 */
	PageInfo<ITaskInst> queryHiTaskFiledByPage(String userId);

	/**
	 * 查询已办任务(已撤销)
	 *
	 * @param userId
	 * @return
	 */
	PageInfo<ITaskInst> queryHiTaskCancelByPage(String userId);

	/**
	 * 根据流程实例id获取当前任务
	 * 
	 * @param prociid
	 * @return
	 */
	List<Task> getTaskByProciid(String prociid);

	List<Map<String, Object>> getNextNode(String procInstanceId);

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
	List<ITask> queryRuTask(String userId, String[] roleId, String processDefKey);

	/**
	 * 查询历史任务的足迹信息
	 * 
	 * @param procInstId 流程实例id
	 * @return
	 */
	List<ITaskInst> queryFootMarkInfo(String procInstId);

	/**
	 * 签收流程实例的第一个任务
	 * @param procInstId 流程实例id
	 * @param userId 用户id
	 */
	Task claimProcessInstanceFristTask(String procInstId, String userId);
	
	/**
	 * 获取可委托权限
	 * @param userId 用户id
	 * @param roleId 角色id集合
	 * @return
	 */
	List<DelegateInfo> queryDelegateAuth(String userId, String[] roleId);
	
	/**
	 * 委托完成任务
	 * @param delegateId 委托记录id
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @param assignee 委托人id
	 * @param variables 流程变量
	 * @param result 处理结果
	 * @param resultDesc 处理意见
	 */
	void delegateComplate(String delegateId, String taskId, String userId, String assignee, Map<String, Object> variables
			,String result, String resultDesc);
	
	/**
	 * 委托回退任务
	 * @param delegateId 委托记录id
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @param assignee 委托人id
	 * @param variables 流程变量
	 * @param result 处理结果
	 * @param resultDesc 处理意见
	 */
	void delegateRollback(String delegateId, String taskId, String userId, String assignee, Map<String, Object> variables
			,String result, String resultDesc);

	/**
	 * 委托回退任务，并且让之前处理人签收任务
	 * @param delegateId 委托记录id
	 * @param taskId 任务id
	 * @param userId 用户id
	 * @param assignee 委托人id
	 * @param variables 流程变量
	 * @param result 处理结果
	 * @param resultDesc 处理意见
	 */
	void delegateRollbackAndClaim(String delegateId, String taskId, String userId, String assignee, Map<String, Object> variables
			,String result, String resultDesc);
	
	/**
	 * 新增一条委托
	 * @param delegateInfo 委托信息
	 */
	DelegateInfo addDelegate(DelegateInfo delegateInfo);
	
	/**
	 * 查询委托列表
	 * @param userId
	 * @return
	 */
	List<DelegateInfo> queryDelegateInfoList(String userId);
	
	/**
	 * 根据委托记录id删除委托记录
	 * @param delegateId
	 */
	void deleteDelegate(String delegateId);
	
	/**
	 * 查询可委托规则
	 * @param processDefId 流程定义id
	 * @param activityId	活动id
	 * @return
	 */
	List<DelegateRule> queryDelegateRuleList(String processDefId, String activityId);
	
	/**
	 * 新增委托规则
	 * @param delegateRuleList
	 */
	void addDelegateRule(List<DelegateRule> delegateRuleList);
	
	/**
	 * 删除委托记录的委托规则
	 * @param delegateId 委托记录id
	 */
	void deleteDelegateRule(String delegateId);
	
	/**
	 * 查询委托记录下委托规则列表
	 * @param delegateId 委托记录id
	 * @return
	 */
	List<DelegateRule> queryHaveDelegateRuleList(String delegateId);
}
