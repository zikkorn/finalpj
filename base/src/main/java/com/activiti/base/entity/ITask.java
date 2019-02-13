package com.activiti.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ITask implements Serializable {
	private static final long serialVersionUID = -4342426070934142235L;
	// 流程实例id
	private String procInstId;
	// 模型名称
	private String modelName;
	// 活动id
	private String activityId;
	// 流程id
	private String processDefKey;
	//
	private String formKey;
	// 部署id
	private String deploymentId;
	// 活动名称
	private String activityName;
	// 开始时间
	private Date startTime;
	// 是否超时
	private String isOverTime;
	// 状态
	private String state;
	// 流程定义id
	private String processDefId;
	// 任务id
	private String taskId;
	// 结束时间
	private Date endTime;
	// 指定人
	private String assignee;
	// 流程业务id
	private String bussinessKey;

	private String startUserId;
	//代办人
	private String owner;
	//被委托人
	private String attorney;
	//委托人
	private String delegater;
	//委托记录id
	private String delegateId;

    //委托信息
    private List<DelegateInfo> delegates;

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	} 

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getProcessDefKey() {
		return processDefKey;
	}

	public void setProcessDefKey(String processDefKey) {
		this.processDefKey = processDefKey;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getIsOverTime() {
		return isOverTime;
	}

	public void setIsOverTime(String isOverTime) {
		this.isOverTime = isOverTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * @return the bussinessKey
	 */
	public String getBussinessKey() {
		return bussinessKey;
	}

	/**
	 * @param bussinessKey
	 *            the bussinessKey to set
	 */
	public void setBussinessKey(String bussinessKey) {
		this.bussinessKey = bussinessKey;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public List<DelegateInfo> getDelegates() {
		return delegates;
	}

	public void setDelegates(List<DelegateInfo> delegates) {
		this.delegates = delegates;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAttorney() {
		return attorney;
	}

	public void setAttorney(String attorney) {
		this.attorney = attorney;
	}

	public String getDelegater() {
		return delegater;
	}

	public void setDelegater(String delegater) {
		this.delegater = delegater;
	}

	public String getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(String delegateId) {
		this.delegateId = delegateId;
	}
}
