package com.tqy.cams.bean;

import com.activiti.base.entity.ITaskInst;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 已办任务
 * 
 * @ClassName: ITaskInst
 */
public class MyTaskInst extends ITaskInst implements Serializable {
	private static final long serialVersionUID = 2350044243073107600L;
	// 指定人名称
	private String assigneeName;
	// 被委托人姓名
	private String attorneyName;
	@JsonIgnore
	private Integer index;

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getAttorneyName() {
		return attorneyName;
	}

	public void setAttorneyName(String attorneyName) {
		this.attorneyName = attorneyName;
	}
}
