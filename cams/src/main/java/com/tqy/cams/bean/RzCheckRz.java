package com.tqy.cams.bean;

import java.io.Serializable;

import com.activiti.base.entity.ITask;

public class RzCheckRz extends ITask implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String applicationId;	//审核的认证申请id
	private String checkUserName;	//审核人姓名
	private String checkDate; 	//审核时间
	private String suggestion;	//审核意见
	private String result;		//审核结果
	
	// 任务类型  0：我的任务，1：代办任务，2：被委托任务
    private int taskType;
    // 任务类型名称
    private String taskTypeName;
	
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public String getTaskTypeName() {
		return taskTypeName;
	}
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}
	public String getCheckUserName() {
		return checkUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
}
