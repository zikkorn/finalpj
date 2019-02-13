package com.activiti.entity.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 流程返回描述
 *
 **/
public class BaseStatic {

    //加载配置文件
    private static Properties prop = new Properties();

    static {
        try {
            // 解决中文乱码问题
            prop.load(new InputStreamReader(BaseStatic.class.getClassLoader().getResourceAsStream("base.properties"), "UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 服务端取分页属性值的key
    public static final String PAGE_PAGESIZE_KEY = prop.getProperty("page.pagesize.key");
    public static final String PAGE_PAGESIZE_VALUE = prop.getProperty("page.pagesize.value");
    public static final String PAGE_PAGENUM_KEY = prop.getProperty("page.pagenum.key");
    public static final String PAGE_PAGENUM_VALUE = prop.getProperty("page.pagenum.value");

    /**
     * client访问路径
     */
    public static final String ACTIVITI_CLIENT_URL = prop.getProperty("activiti.client.url");

    /**
     * client获取用户组织角色接口路径
     */
    public static final String ACTIVITI_CLIENT_USER_ROLE_ORG = prop.getProperty("activiti.client.user.role.org");

    /**
     * client获取用户组织角色接口路径
     */
    public static final String ACTIVITI_CLIENT_USER_ALL_ROLE = prop.getProperty("activiti.client.user.all.role");
}
