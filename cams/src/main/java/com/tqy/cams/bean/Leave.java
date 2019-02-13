package com.tqy.cams.bean;

import com.activiti.base.entity.ITask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 请假实体类
 **/
public class Leave extends ITask implements Serializable{
    private static final long serialVersionUID = 1915534687958424287L;
    private String id;
    private String content;//请假内容
    private String applicationUserId;//请假人id
    private String applicationUserName;//请假人姓名
    private String applicationUserNum;//工号
    private String applicationUserDepart;//部门
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date applicationStartTime;//开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date applicationEndTime;//结束时间
    private int leaveType;//请假类型（0：事假，1：病假，2：婚假）

    private int auditLeaderstatus;//领导审核状态
    private String auditLeadername;//领导审核人名称
    private int auditHRstatus;//人事部审核状态
    private String auditHRname;//人事部审核人名称

    private String title;//标题

    @JsonIgnore
    private Integer index;

    // 任务类型  0：我的任务，1：代办任务，2：被委托任务
    private int taskType;
    // 任务类型名称
    private String taskTypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(String applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    public String getApplicationUserName() {
        return applicationUserName;
    }

    public void setApplicationUserName(String applicationUserName) {
        this.applicationUserName = applicationUserName;
    }

    public String getApplicationUserNum() {
        return applicationUserNum;
    }

    public void setApplicationUserNum(String applicationUserNum) {
        this.applicationUserNum = applicationUserNum;
    }

    public String getApplicationUserDepart() {
        return applicationUserDepart;
    }

    public void setApplicationUserDepart(String applicationUserDepart) {
        this.applicationUserDepart = applicationUserDepart;
    }

    public Date getApplicationStartTime() {
        return applicationStartTime;
    }

    public void setApplicationStartTime(Date applicationStartTime) {
        this.applicationStartTime = applicationStartTime;
    }

    public Date getApplicationEndTime() {
        return applicationEndTime;
    }

    public void setApplicationEndTime(Date applicationEndTime) {
        this.applicationEndTime = applicationEndTime;
    }

    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }

    public String getAuditLeadername() {
        return auditLeadername;
    }

    public void setAuditLeadername(String auditLeadername) {
        this.auditLeadername = auditLeadername;
    }

    public String getAuditHRname() {
        return auditHRname;
    }

    public void setAuditHRname(String auditHRname) {
        this.auditHRname = auditHRname;
    }

    public int getAuditLeaderstatus() {
        return auditLeaderstatus;
    }

    public void setAuditLeaderstatus(int auditLeaderstatus) {
        this.auditLeaderstatus = auditLeaderstatus;
    }

    public int getAuditHRstatus() {
        return auditHRstatus;
    }

    public void setAuditHRstatus(int auditHRstatus) {
        this.auditHRstatus = auditHRstatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }
}
