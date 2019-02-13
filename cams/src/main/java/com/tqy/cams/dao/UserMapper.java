package com.tqy.cams.dao;

import com.tqy.cams.bean.*;

import java.util.List;
import java.util.Map;

/**
 * 用户
 **/
public interface UserMapper {

    List<Role> getRoleUser();

    String getUserByName(String userName, String userPwd);
    
    String getIdByName(String userName);

    List<Map<String,String>> getUsers();

    String[] getUserRoles(String userId);

    List<Map<String,Object>> getUserInfo(String userId);

    List<User> getUsersNoRole();

    List<Map<String,String>> getRolesByUserIds(String[] userIds);
   
}

