package com.tqy.cams.service.impl;

import com.activiti.base.entity.common.ResultMessage;
import com.tqy.cams.bean.*;
import com.tqy.cams.bean.common.BaseStatic;
import com.tqy.cams.dao.UserMapper;
import com.tqy.cams.service.UserService;
import com.tqy.cams.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户
 **/
@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Role> getRoleUser() {
        return userMapper.getRoleUser();
    }

    @Override
    public ResultMessage login(String userName, String userPwd) {
        String userId = userMapper.getUserByName(userName, userPwd);
        if(StringUtils.isNotBlank(userId)){
        	List<Map<String, Object>> list = userMapper.getUserInfo(userId);
        	int isAdmin = 1;
        	for(Map<String, Object> s : list) {
        		if(s.get("roleName").toString().equals("管理员")) {
        			isAdmin = 0;
        		}
        	}
        	Map<String, Object> rs = new HashMap<String, Object>();
        	rs.put("userId", userId);
        	rs.put("role", isAdmin);
            return new ResultMessage(0,"登录成功",rs);
        }
        return new ResultMessage(1,"登录失败");
    }

    @Override
    public List<Map<String,String>> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public String[] getUserRoles(String userId) {
        String[] roles = userMapper.getUserRoles(userId);
        if(roles == null){
            return new String[0];
        }
        return roles;
    }

    @Override
    public ResultMessage getUserInfo(String userId) {
        List<Map<String,Object>> list = userMapper.getUserInfo(userId);
        if(list != null && list.size() > 0){
            return new ResultMessage(BaseStatic.SUCCESS_CODE,"成功",list.get(0));
        }
        return new ResultMessage(BaseStatic.ERROR_CODE,"用户不存在");
    }

    @Override
    public List<User> getUsersNoRole() {
        return userMapper.getUsersNoRole();
    }

    @Override
    public List<Map<String,String>> getRolesByUserIds(String userIdsStr) {
        String[] userIds = null;
        if(!StringUtil.isNullOrBlank(userIdsStr)){
            userIds = userIdsStr.split(",");
        }
        return userMapper.getRolesByUserIds(userIds);
    }
}
