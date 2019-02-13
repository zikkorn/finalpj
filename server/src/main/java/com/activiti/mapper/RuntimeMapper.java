package com.activiti.mapper;

import org.apache.ibatis.annotations.Param;

public interface RuntimeMapper {

	/**
	 * 设置业务id
	 * @param procInstId 流程实例id
	 * @param buzId	 业务id
	 */
	void setBusinessKey(@Param("procInstId")String procInstId, @Param("buzId")String buzId);

}
