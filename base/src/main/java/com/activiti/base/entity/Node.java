package com.activiti.base.entity;

import java.io.Serializable;

/**
 * 活动节点
 */
public class Node implements Serializable{

	private static final long serialVersionUID = -7388993013042305549L;
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
