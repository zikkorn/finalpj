package com;

import com.activiti.base.service.IHistoryService;
import com.activiti.base.service.IRepositoryService;
import com.activiti.base.service.IRuntimeService;
import com.activiti.base.service.ITaskService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@SpringBootApplication
@MapperScan("com.activiti.mapper")
public class ActivityApp
{
    public static void main(String[] args){
        SpringApplication.run(ActivityApp.class, args);
    }

    @Autowired
    private IHistoryService historyService;
    @Bean(name = "/historyService")
    public HessianServiceExporter historyService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(historyService);
        exporter.setServiceInterface(IHistoryService.class);
        return exporter;
    }

    @Autowired
    private IRepositoryService repositoryService;
    @Bean(name = "/repositoryService")
    public HessianServiceExporter repositoryService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(repositoryService);
        exporter.setServiceInterface(IRepositoryService.class);
        return exporter;
    }

    @Autowired
    private IRuntimeService runtimeService;
    @Bean(name = "/runtimeService")
    public HessianServiceExporter runtimeService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(runtimeService);
        exporter.setServiceInterface(IRuntimeService.class);
        return exporter;
    }

    @Autowired
    private ITaskService taskService;
    @Bean(name = "/taskService")
    public HessianServiceExporter taskService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(taskService);
        exporter.setServiceInterface(ITaskService.class);
        return exporter;
    }

}	