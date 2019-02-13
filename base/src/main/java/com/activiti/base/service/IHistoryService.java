package com.activiti.base.service;

import javax.servlet.http.HttpServletResponse;

public interface IHistoryService {
    /**
     * 查看流程图
     * @param procInstId    流程实例id
     */
    void showActivitiImage(String procInstId, Boolean isShowHighLight, HttpServletResponse response);

    byte[] getActivitiImageByte(String procInstId, Boolean isShowHighLight);

}
