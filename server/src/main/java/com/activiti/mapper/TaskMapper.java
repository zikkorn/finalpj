package com.activiti.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.activiti.base.entity.DelegateInfo;
import com.activiti.base.entity.DelegateRule;
import com.activiti.base.entity.ITask;
import com.activiti.base.entity.ITaskInst;
import com.activiti.base.entity.ProcInst;

public interface TaskMapper {

	/**
	 * 根据用户和角色id查询任务
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<ITask> queryTask(@Param("userId") String userId, @Param("roleId") String[] roleId);

	/**
	 * 分页查询用户任务
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<ITask> queryTaskByPage(@Param("userId") String userId, @Param("roleId") String[] roleId);

	/**
	 * 添加足迹信息
	 * 
	 * @param taskId
	 * @param result
	 * @param resultDesc
	 * @return
	 */
	int insertFootpoint(@Param("taskId") String taskId, @Param("result") String result,
			@Param("resultDesc") String resultDesc);

	/**
	 * 查询已办任务(所有我经手的)
	 * 
	 * @param userId
	 * @return
	 */
	List<ITaskInst> queryHiTaskAll(@Param("userId") String userId);

	/**
	 * 查询已办任务(我发起的)
	 * 
	 * @param userId
	 * @return
	 */
	List<ProcInst> queryHiTaskStartByPage(@Param("userId") String userId);

	/**
	 * 查询已办任务(已归档的)
	 * 
	 * @param userId
	 * @return
	 */
	List<ITaskInst> queryHiTaskFiledByPage(@Param("userId") String userId);

	/**
	 * 查询已办任务(已撤销的)
	 * 
	 * @param userId
	 * @return
	 */
	List<ITaskInst> queryHiTaskCancelByPage(@Param("userId") String userId);

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
	List<ITask> queryRuTask(Map<String,Object> param);

	/**
	 * 查询历史任务的足迹信息
	 * 
	 * @param userId
	 * @return
	 */
	List<ITaskInst> queryFootMarkInfo(@Param("procInstId") String procInstId);

	/**
	 * 获取当前用户的被委托人ids
	 * @param userId
	 * @return
	 */
    List<String> getAssigneeByUserId(String userId);
    
    /**
	 * 委托流转记录
	 * @param id 主键id
	 * @param taskId	任务id
	 * @param userId	用户id
	 * @param assignee	委托人id
	 */
	void complateDelegate(@Param("id")String id, @Param("delegateId")String delegateId, @Param("taskId")String taskId, @Param("userId")String userId,
			@Param("assignee")String assignee);

	/**
	 * 新增委托
	 * @param delegateInfo
	 */
	void addDelegate(DelegateInfo delegateInfo);

	/**
	 * 查询委托记录列表
	 * @param userId 委托人id
	 * @return
	 */
	List<DelegateInfo> queryDelegateInfoList(@Param("userId")String userId);

	/**
	 * 删除委托
	 * @param delegateId
	 */
	void deleteDelegate(@Param("delegateId")String delegateId);

	/**
	 * 获取最近历史活动code
	 * @param processInstId 流程实例id
	 * @return
	 */
	String queryLastActivityCode(@Param("processInstId")String processInstId);

	/**
	 * 批量新增委托规则
	 * @param delegateRuleList
	 */
	void addDelegateRule(List<DelegateRule> list);

	/**
	 * 删除委托记录下的委托规则
	 * @param delegateId
	 */
	void deleteDelegateRule(@Param("delegateId")String delegateId);

	/**
	 * 查询委托记录下委托规则列表
	 * @param delegateId
	 * @return
	 */
	List<DelegateRule> queryHaveDelegateRuleList(@Param("delegateId")String delegateId);

	/**
	 * 查询被委托的委托记录
	 * @param userId 被委托人id
	 * @return
	 */
	List<DelegateInfo> queryDelegateUserInfo(@Param("userId")String userId);
}
