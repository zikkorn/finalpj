package com.activiti.base.service;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;


public interface IRuntimeService {
	
	/**
	 * 根据流程定义key启动并返回流程实例对象
	 * @param processKey 流程定义key
	 */
	public ProcessInstance startProcess(String processKey);
	
	/**
	 * 根据流程实例id给流程设置变量map
	 * @param processInstanceId 流程实例id
	 * @param variables 流程变量map
	 */
	public void setVariables(String processInstanceId, Map<String, Object> variables);
	
	/**
	 * 根据流程实例id获取流程变量map
	 * @param processInstanceId 流程实例id
	 * @return
	 */
	public Map<String, Object> getVariables(String processInstanceId);
	
	/**
	 * 根据流程实例id获取流程实例
	 * @param processInstanceId
	 * @return
	 */
	public ProcessInstance getProcessInstanceById(String processInstanceId);

	/**
	 * 启动流程
	 * @param processKey
	 * @param userId 
	 * @return
	 */
    ProcessInstance startProcessInstanceByKey(String processKey, String userId);

	/**
	 * 中止流程
	 * @param procInstId
	 * @param deleteDesc
	 */
	void deleteProcessInstance(String procInstId, String deleteDesc);

    ExecutionEntity getExecutionEntity(String excId);
    

	/**
	 * 设置业务id
	 * @param procInstId 流程实例id
	 * @param buzId	 业务id
	 */
	void setBusinessKey(String procInstId, String buzId);
	
    /**
     * 带业务id启动流程
     * @param processKey
     * @param userId
     * @param buzId
     * @return
     */
    ProcessInstance startProcessInstanceByKey(String processKey, String userId, String buzId);
}
