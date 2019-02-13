package com.activiti.base.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 委托信息
 *
 */
public class DelegateInfo implements Serializable {
	
	private static final long serialVersionUID = -3047866882666660536L;
	//委托id
	private String delegateInfoId;
	//委托人
	private String assignee;
	//被委托人
	private String attorney;
	//开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	//结束时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	//流程名称
	private String processName;
	//流程定义id
	private String processDefId;
	//活动id
	private String activityId;
	//状态
	private String status;
	//活动名称
	private String activityName;
	//活动集合
	private List<Activity> activityList = new ArrayList<Activity>();
	//被委托人名称
	private String attorneyName;
	//委托规则集合
	private List<DelegateRule> ruleList = new ArrayList<DelegateRule>();

	public String getAttorneyName() {
		return attorneyName;
	}

	public void setAttorneyName(String attorneyName) {
		this.attorneyName = attorneyName;
	}

	public String getDelegateInfoId() {
		return delegateInfoId;
	}
	public void setDelegateInfoId(String delegateInfoId) {
		this.delegateInfoId = delegateInfoId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAttorney() {
		return attorney;
	}
	public void setAttorney(String attorney) {
		this.attorney = attorney;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Activity> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public List<DelegateRule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<DelegateRule> ruleList) {
		this.ruleList = ruleList;
	}

}
