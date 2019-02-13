package com.tqy.cams.dao;

import java.util.List;
import java.util.Map;

import com.activiti.base.entity.DelegateInfo;
import com.activiti.base.entity.IProcess;
import com.tqy.cams.bean.MyProcInst;
import com.tqy.cams.bean.MyTaskInst;
import com.tqy.cams.bean.RzApplication;
import com.tqy.cams.bean.RzCheckRz;

/**
 * 入网认证
 * @author sunqian
 *
 */
public interface RzblMapper {
	
	List<RzApplication> getMyTaskByPage(Map<String, Object> map);

    int saveRzApplication(RzApplication rz);
    
    int queryCount(String systemName,String managerDept,String developDept);
    
    List<RzApplication> queryRzAppByCondition(String start, String end,String systemName,String managerDept,String developDept);

    RzApplication getRzApplicationById(String id);

    int updateRzApplication(RzApplication rz);
    
    int saveCheckRzApplication(RzCheckRz check);

    RzCheckRz getCheckRzApplicationById(String applicationId);

    int updateCheckRzApplication(RzCheckRz check);
    
    List<MyProcInst> getMyApplicationByPage(Map<String, Object> map);

    List<MyTaskInst> queryHiTaskAllByPage(Map<String, Object> map);
    
    List<IProcess> queryStartupProcess(Map<String, Object> map);

    List<DelegateInfo> queryDelegateInfoListByPage(List<DelegateInfo> list);

}
