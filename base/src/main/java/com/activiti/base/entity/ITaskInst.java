package com.activiti.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 已办任务
 */
public class ITaskInst implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// 任务id
	private String id;
	// 任务流程id
	private String processDefId;
	// 任务名称
	private String taskName;
	// 指定人
	private String assignee;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 发起人id
	private String startUserId;
	// 描述
	private String duration;
	// 结束原因
	private String deleteReason;
	// 意见
	private String taskResult;
	// 意见描述
	private String taskResultDesc;
	// 时长
	private String hisTaskTime;
	//流程实例id
	private String procInstId;
	//业务id
	private String businessKey;

	private String procName;

	//委托信息
	private List<DelegateInfo> delegates;
	//被委托人
	private String attorney;
	//表单key
	private String formKey;

	// 活动id
	private String activityId;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the processDefId
	 */
	public String getProcessDefId() {
		return processDefId;
	}

	/**
	 * @param processDefId
	 *            the processDefId to set
	 */
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * @param assignee
	 *            the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the startUserId
	 */
	public String getStartUserId() {
		return startUserId;
	}

	/**
	 * @param startUserId
	 *            the startUserId to set
	 */
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the deleteReason
	 */
	public String getDeleteReason() {
		return deleteReason;
	}

	/**
	 * @param deleteReason
	 *            the deleteReason to set
	 */
	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	/**
	 * @return the taskResult
	 */
	public String getTaskResult() {
		return taskResult;
	}

	/**
	 * @param taskResult
	 *            the taskResult to set
	 */
	public void setTaskResult(String taskResult) {
		this.taskResult = taskResult;
	}

	/**
	 * @return the hisTaskTime
	 */
	public String getHisTaskTime() {
		return hisTaskTime;
	}

	/**
	 * @param hisTaskTime
	 *            the hisTaskTime to set
	 */
	public void setHisTaskTime(String hisTaskTime) {
		this.hisTaskTime = hisTaskTime;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getTaskResultDesc() {
		return taskResultDesc;
	}

	public void setTaskResultDesc(String taskResultDesc) {
		this.taskResultDesc = taskResultDesc;
	}

	public List<DelegateInfo> getDelegates() {
		return delegates;
	}

	public void setDelegates(List<DelegateInfo> delegates) {
		this.delegates = delegates;
	}

	public String getAttorney() {
		return attorney;
	}

	public void setAttorney(String attorney) {
		this.attorney = attorney;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}
