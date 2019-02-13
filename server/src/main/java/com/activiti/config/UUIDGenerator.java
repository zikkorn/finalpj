package com.activiti.config;

import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;

public class UUIDGenerator implements IdGenerator {

	@Override
	public String getNextId() {
		return UUID();
	}
	
	public static String UUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

}
