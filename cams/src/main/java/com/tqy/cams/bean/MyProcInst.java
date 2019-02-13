package com.tqy.cams.bean;

import com.activiti.base.entity.ProcInst;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 流程实例
 */
public class MyProcInst extends ProcInst implements Serializable {
	private static final long serialVersionUID = -8426940188664414676L;
	@JsonIgnore
	private int index;
	//启动者名称
	private String startUserName;
	//状态类型
	private String status;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
