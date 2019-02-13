package com.activiti.base.util;

import com.caucho.hessian.client.HessianProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class HessianServiceFactory extends HessianProxyFactory {
    static Logger log = LoggerFactory.getLogger(HessianServiceFactory.class);

    private static HessianProxyFactory factory = new HessianProxyFactory();

    public static <T> T getService(Class<T> tClass ,String url){
        T t = null;
        try {
            t = (T) factory.create(tClass, url + "/" + tClass.getSimpleName());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void main(String[] args) {

    }
}