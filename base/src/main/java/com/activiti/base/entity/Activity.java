package com.activiti.base.entity;


import java.io.Serializable;

/**
 * 活动
 *
 */
public class Activity implements Serializable{
	private static final long serialVersionUID = -6297429757462125679L;
	//活动id
	private String activityId;
	//活动名称
	private String activityName;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	

}
