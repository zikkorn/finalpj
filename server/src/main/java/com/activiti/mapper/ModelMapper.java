package com.activiti.mapper;

import com.activiti.base.entity.IProcess;
import com.activiti.base.entity.RepositoryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelMapper {
    /**
     * 获取流程启动者
     * @param processDefId 流程定义id
     * @return
     */
    List<IProcess> getProcessStarter(@Param("processDefId") String processDefId);
    int getProcessStarterCount(@Param("processDefId")String processDefId);

    /**
     * 更新流程启动者
     * @param userIds     启动用户id，多个逗号分隔
     * @param groupIds    启动角色id，多个逗号分隔
     * @param processDefId   流程定义id
     * @return
     */
    int updateProcessStarter(@Param("userIds")String userIds, @Param("groupIds")String groupIds, @Param("processDefId")String processDefId);

    /**
     * 插入流程启动者
     * @param id        主键id
     * @param userIds   启动用户id，多个逗号分隔
     * @param groupIds  启动角色id，多个逗号分隔
     * @param processDefId 流程定义id
     * @return
     */
    int insertProcessStarter(@Param("id")String id, @Param("userIds")String userIds, @Param("groupIds")String groupIds, @Param("processDefId")String processDefId);
    /**
     * 查询流程列表
    */
    List<RepositoryModel> findModelByPage(String catalogId);

    /**
     * 分页查询用户或者角色可启动流程
     * @param userId    用户或者角色id
     * @return
     */
    List<IProcess> getStartupProcess(@Param("userId") String userId);
    
	/**
	 * 分页获取所有流程定义集合
	 * @return
	 */
	List<IProcess> queryProcessDefListByPage();
	
	/**
	 * 获取所有已激活流程定义集合
	 * @return
	 */
	List<IProcess> queryProcessDefUsedList();
}
