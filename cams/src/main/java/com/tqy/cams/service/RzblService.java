package com.tqy.cams.service;

import com.activiti.base.entity.DelegateRule;
import com.activiti.base.entity.common.PageResult;
import com.activiti.base.entity.common.ResultMessage;
import com.tqy.cams.bean.RzApplication;
import com.tqy.cams.bean.RzCheckRz;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 认证办理
 **/
public interface RzblService {

    /**
     * 我的任务
     * @param userId
     * @param processDefKey
     * @param leave
     * @return
     */
    PageResult queryMyTask(String userId, String processDefKey);

    /**
     * 查询可启动流程
     * @param userId
     * @return
     */
    PageResult queryStartupProcess(String userId);

    /**
     * 启动流程
     * @param userId
     * @param processDefKey
     * @param buzId    业务id
     * @return
     */
    ResultMessage startProcess(String userId, String processDefKey, String buzId);

    /**
     * 签收任务
     * @param taskId
     * @param userId
     * @return
     */
    ResultMessage claimTask(String taskId, String userId);

    /**
     * 取消签收任务
     * @param taskId	任务id
     * @return
     */
    ResultMessage unclaimTask(String taskId);

    /**
     * 发送任务
     * @param taskId
     * @param userId
     * @param result
     *@param resultDesc @return
     */
    ResultMessage sendTask(String taskId, String userId, String result, String resultDesc);

    /**
     * 回退任务
     * @param taskId	
     * @param userId	
     * @param result
     *@param resultDesc @return
     */
    ResultMessage backTask(String taskId, String userId, String result, String resultDesc);

    /**
     * 给流程实例设置业务id
     * @param prciId    流程实例id
     * @param buzId     业务id
     * @return
     */
    ResultMessage setBusinessId(String prciId, String buzId);

    /**
     * 设置流程变量
     * @param prciId	流程实例ID
     * @return
     */
    ResultMessage setVariables(String prciId);

    /**
     * 获取流程变量
     * @param prciId	流程实例ID
     * @return
     */
    ResultMessage getVariables(String prciId);
    
    /**
     * 文件上传
     * @param file
     * @param dirPath
     * @return
     */
    ResultMessage upload(String applicationId, MultipartFile file, String dirPath);

    /**
     * 保存入网认证申请表单数据
     * @param rz	
     * @param prciId	流程实例ID
     * @param taskId	任务id
     */
    ResultMessage updateRzApplication(RzApplication rz,String userId);

    /**
     * 查看所有认证表单
     * @param systemName
     * @param managerDept
     * @param developDept
     * @return
     */
    ResultMessage queryAllRzApp(String pageNo, String pageSize, String systemName, String managerDept, String developDept);
   
    /**
     * 查看认证表单和结果
     * @param id
     * @return
     */
    ResultMessage getRzApplication(String id);
    
    /**
     * 保存入网认证审核表单数据
     * @param check	
     * @param prciId	流程实例ID
     * @param taskId	任务id
     */
    ResultMessage updateCheckRzApplication(RzCheckRz check,String userId);

    ResultMessage getCheckRzApplication(String applicationId);

    /**
     * 我的申请
     * @param userId
     * @param processDefKey
     * @return
     */
    PageResult myApplication(String userId, String processDefKey);

    /**
     * 我的已办任务
     * @param userId
     * @param rz
     * @return
     */
    PageResult queryHiTaskAll(String userId);

    void showImg(String prciId, String procDefId, HttpServletResponse response);

    ResultMessage deleteProcess(String prciId);

    ResultMessage getAuth(String userId);

    /**
     * 新增委托
    */
    ResultMessage addDelegate(String jsonStr);
    /**
     * 委托列表
     */
    ResultMessage queryDelegateInfoList(String userId);
    
	/**
	 * 被委托人发送任务
	 * @param taskId
	 * @param userId
	 * @param result
	 * @param resultDesc
	 * @param delegater
	 * @param delegateId
	 * @return
	 */
	ResultMessage sendTaskDelegate(String taskId, String userId, String result,
			String resultDesc, String delegater, String delegateId);

	/**
	 * 删除委托
	 * @param id
	 */
	void deleteDelegate(String id);
	
	/**
	 * 被委托人回退任务
	 * @param taskId
	 * @param userId
	 * @param result
	 * @param resultDesc
	 * @param delegater
	 * @param delegateId
	 * @return
	 */
	ResultMessage backTaskDelegate(String taskId, String userId, String result,
			String resultDesc, String delegater, String delegateId);

    /**
     * 获取委托的业务规则
     * @return
     */
    ResultMessage queryDelegateList(String processDefId, String activityId);

    ResultMessage addDelegateRule(List<DelegateRule> rules);

    ResultMessage getFootpoint(String prciId);
}
