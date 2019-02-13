package com.activiti.service.impl;

import java.util.Map;

import com.activiti.config.HessianService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activiti.mapper.RuntimeMapper;
import com.activiti.base.service.IRuntimeService;

@HessianService
public class IRuntimeServiceImpl implements IRuntimeService {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private RuntimeMapper runtimeMapper;

	@Override
	public ProcessInstance startProcess(String processKey) {
		return runtimeService.startProcessInstanceByKey(processKey);
	}

	@Override
	public void setVariables(String processInstanceId,
			Map<String, Object> variables) {
		runtimeService.setVariables(processInstanceId, variables);
	}

	@Override
	public Map<String, Object> getVariables(String processInstanceId) {
		return runtimeService.getVariables(processInstanceId);
	}

	@Override
	public ProcessInstance getProcessInstanceById(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		return processInstance;
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processKey, String userId) {
		identityService.setAuthenticatedUserId(userId);
		return runtimeService.startProcessInstanceByKey(processKey);
	}

    @Override
    public void deleteProcessInstance(String procInstId, String deleteDesc) {
        runtimeService.deleteProcessInstance(procInstId,deleteDesc);
    }

    @Override
    public ExecutionEntity getExecutionEntity(String excId) {
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
		return execution;
    }

	@Override
	public void setBusinessKey(String procInstId, String buzId) {
		runtimeMapper.setBusinessKey(procInstId,buzId);
	}

	@Override
	public ProcessInstance startProcessInstanceByKey(String processKey,
			String userId, String buzId) {
		identityService.setAuthenticatedUserId(userId);
		return runtimeService.startProcessInstanceByKey(processKey, buzId);
	}

}
