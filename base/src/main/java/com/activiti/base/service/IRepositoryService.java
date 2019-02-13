package com.activiti.base.service;

import com.activiti.base.entity.IProcess;
import com.activiti.base.entity.RepositoryModel;
import com.activiti.base.entity.common.ResultMessage;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.validation.ValidationError;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IRepositoryService {

    /**
     * 校验流程设计
     * @param jsonXml
     * @return
     */
    List<ValidationError> validateProcess(String jsonXml);

    /**
     * 部署流程
     * @param modelId
     */
    ResultMessage deploy(String modelId);

    /**
     * 保存流程启动者
     * @param processDefId 流程定义id
     * @param metaInfo  流程信息
     * @return
     */
    int updateStart(String processDefId, String metaInfo);

    /**
     * 从流程设计数据中获取流程启动者
     * @param jsonXml
     * @return
     */
    Map<String,String> getProcessStarterForJsonXml(String jsonXml);

    /**
     * 从数据库中获取流程启动者
     * @param processDefId
     * @return
     */
    List<IProcess> getProcessStarterForDB(String processDefId);

    /**
     * 查询用户或者角色的流程启动权限
     * @param userId        用户id或者角色id
     * @param processDefId 流程定义id
     * @return
     */
    List<IProcess> getProcessStartupAuth(String userId, String processDefId);

    /**
     * 分页查询用户或者角色可启动流程
     * @param userId 用户或者角色id
     * @return
     */
    List<IProcess> getStartupProcess(String userId);

    /**
     * 创建模型
     * @param key
     * @param name
     * @param desc
     * @return
     */
    String createModeler(String key, String name, String desc);

    /**
     * 查询模型列表
    */
    List<RepositoryModel> findModel(@Param("catalogId") String catalogId);
    /**
     * 更新模型
    */
    void updateModel(String modelId, String name, String desc);

    void deleteModel(String modelId);

    /**
     * 通过taskId获取流程定义对象
     * @param taskId
     * @return
     */
    ProcessDefinition getProcessDefinition(String taskId);

	/**
	 * 分页获取所有流程定义集合
	 * @return
	 */
	List<IProcess> queryProcessDefListByPage();
	
	/**
	 * 根据流程定义id挂起流程
	 * @param processdefId 流程定义id
	 */
	void suspendProcessDef(String processdefId);
	
	/**
	 * 根据流程定义id激活流程
	 * @param processDefId 流程定义id
	 */
	void activateProcessDef(String processDefId);
	

}
