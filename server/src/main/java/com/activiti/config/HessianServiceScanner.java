package com.activiti.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.stereotype.Component;

/**
 * 自动扫面@HessianService注解  注入hessian服务
 */
@Component
public class HessianServiceScanner implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 获取HessianService注解的类
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(HessianService.class);
        for (String beanName : beanNames) {
            String className = beanFactory.getBeanDefinition(beanName).getBeanClassName();
            Class<?> clasz = null;
            try {
                clasz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeanInitializationException(e.getMessage(), e);
            }
            String hessianServiceBeanName = "/" + beanName.replace("Impl", "");

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(HessianServiceExporter.class);
            builder.addPropertyReference("service", beanName);
            builder.addPropertyValue("serviceInterface",clasz.getInterfaces()[0].getName());
            ((BeanDefinitionRegistry) beanFactory).registerBeanDefinition(hessianServiceBeanName, builder.getBeanDefinition());
        }
    }

}