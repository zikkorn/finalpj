package com.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MyProcessEngineCfgConfigurer implements
		ProcessEngineConfigurationConfigurer {

	@Override
	public void configure(
			SpringProcessEngineConfiguration processEngineConfiguration) {
		UUIDGenerator idGenerator = new UUIDGenerator();
		processEngineConfiguration.setIdGenerator(idGenerator);
	}

}
