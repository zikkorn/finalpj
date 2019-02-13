package com.tqy.cams.bean.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 流程返回描述
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

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 1;


    // 服务端取分页属性值的key
    public static final String PAGE_PAGESIZE_KEY = prop.getProperty("page.pagesize.key");
    public static final String PAGE_PAGESIZE_VALUE = prop.getProperty("page.pagesize.value");
    public static final String PAGE_PAGENUM_KEY = prop.getProperty("page.pagenum.key");
    public static final String PAGE_PAGENUM_VALUE = prop.getProperty("page.pagenum.value");

    /**
     * activiti服务访问路径
     */
    public static final String ACTIVITI_SERVER_URL = prop.getProperty("activiti.server.url");
}
