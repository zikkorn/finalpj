package com.tqy.cams.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tqy.cams.bean.common.BaseStatic;

import javax.servlet.http.HttpServletRequest;

public class PageUtil {

    public static String getPageNum(){
        // 获取此次请求的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String pageNum = request.getParameter(BaseStatic.PAGE_PAGENUM_KEY);
        if(StringUtils.isBlank(pageNum)){
            pageNum = BaseStatic.PAGE_PAGENUM_VALUE;
        }
        return pageNum;
    }

    public static String getPageSize(){
        // 获取此次请求的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String pageSize = request.getParameter(BaseStatic.PAGE_PAGESIZE_KEY);
        if(StringUtils.isBlank(pageSize)){
            pageSize = BaseStatic.PAGE_PAGESIZE_VALUE;
        }
        return pageSize;
    }


}
