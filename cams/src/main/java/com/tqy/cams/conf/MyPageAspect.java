package com.tqy.cams.conf;

import com.github.pagehelper.PageHelper;
import com.tqy.cams.bean.common.BaseStatic;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分页切面
 **/
@Component
@Aspect
public class MyPageAspect {
    private final Logger log = LoggerFactory.getLogger(MyPageAspect.class);
    /**
     * 定义切点
     */
    @Pointcut("execution(* com.tqy.cams.dao.*.*ByPage(..))")
    public void initPage(){}

    @Around("initPage()")
    public Object doBefore(ProceedingJoinPoint pjp) throws Throwable {
        List result = null;
        // 前置通知
        // 获取此次请求的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String pageNum = request.getParameter(BaseStatic.PAGE_PAGENUM_KEY);
        String pageSize = request.getParameter(BaseStatic.PAGE_PAGESIZE_KEY);
        if(StringUtils.isBlank(pageNum)){
            pageNum = BaseStatic.PAGE_PAGENUM_VALUE;
        }
        if(StringUtils.isBlank(pageSize)){
            pageSize = BaseStatic.PAGE_PAGESIZE_VALUE;
        }
        log.info("分页参数 pageNum:"+pageNum + ",pageSize:" + pageSize);
        PageHelper.startPage(Integer.valueOf(pageNum),Integer.valueOf(pageSize));

        //执行目标方法
        result = (List) pjp.proceed();
        // 返回通知
        return result;
    }

}
