package com.tqy.cams.controller;

import com.activiti.base.entity.common.ResultMessage;
import com.tqy.cams.bean.User;
import com.tqy.cams.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @RequestMapping("login")
    public ResultMessage login(String userName, String userPwd, HttpServletRequest request){
        ResultMessage rm = userService.login(userName,userPwd);
        return rm;
    }

    /**
     * 获取角色用户信息
     * @return
     */
    @RequestMapping("getUsers")
    public ResultMessage getUsers(){
        List<Map<String,String>> list = userService.getUsers();
        return new ResultMessage(0,"成功",list);
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("getUsersNoRole")
    public ResultMessage getUsersNoRole(){
        List<User> list = userService.getUsersNoRole();
        return new ResultMessage(0,"成功",list);
    }

    /**
     * 获取用户所有角色
     * @return
     */
    @RequestMapping("getUserRoles")
    public ResultMessage getUserRoles(String userIds){
        List<Map<String,String>> list = userService.getRolesByUserIds(userIds);
        return new ResultMessage(0,"成功",list);
    }
    
    /**
     * 获取个人信息数据
     */
    @RequestMapping("getUserInfo")
    public ResultMessage getUserInfo(String userId) {
        return userService.getUserInfo(userId);
    }

}
