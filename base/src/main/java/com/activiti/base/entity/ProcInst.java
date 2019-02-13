package com.activiti.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 流程实例
 */
public class ProcInst implements Serializable {
	
	private static final long serialVersionUID = 4953879131389114469L;
	//流程实例id
	private String procInstId;
	//业务id
	private String businessKey;
	//流程定义id
	private String processDefId;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	//启动者id
	private String startUserId;
	//开始节点
	private String startActId;
	//结束节点
	private String endActId;
	//结束原因
	private String deleteReason;
	//名称
	private String name;
	//当前节点
	private List<Node> nodes;
	//表单标识
	private String formKey;

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
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

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
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

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartActId() {
		return startActId;
	}

	public void setStartActId(String startActId) {
		this.startActId = startActId;
	}

	public String getEndActId() {
		return endActId;
	}

	public void setEndActId(String endActId) {
		this.endActId = endActId;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
}
