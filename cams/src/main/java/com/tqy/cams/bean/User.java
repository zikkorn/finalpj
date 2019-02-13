package com.tqy.cams.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 **/
public class User implements Serializable{
    private static final long serialVersionUID = -3631802494878211337L;
    private String userId;
    private String userName;
    private String userPwd;

    private List<String> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
