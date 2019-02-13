package com.activiti.controller;

import com.activiti.base.entity.RepositoryModel;
import com.activiti.base.entity.common.PageResult;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.CatalogService;
import com.activiti.base.service.IRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模型相关controller
 *
 **/
@RestController
@RequestMapping("model")
public class ModelRestResource {

    @Autowired
    private IRepositoryService repositoryService;

    @Autowired
    private CatalogService catalogService;

    /**
     * 创建模型
     */
    @RequestMapping("create")
    public ResultMessage create(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        String catalogId = request.getParameter("catalogId");
        String modelId = repositoryService.createModeler(key,name,desc);
        catalogService.addCatalogModel(catalogId,modelId);
        return new ResultMessage(0,"创建成功");
    }

    /**
     * 查询模型列表
     */
    @RequestMapping("findModelList")
    public PageResult findModelList(HttpServletRequest request, HttpServletResponse response) {
        String catalogId = request.getParameter("catalogId");
        //repositoryService.findModelByCatalogId(catalogId);
        List<RepositoryModel> list = repositoryService.findModel(catalogId);

        return new PageResult(0,"查询成功",list);
    }

    /**
     * 更新流程
     */
    @RequestMapping("updateModel")
    public ResultMessage updateModel(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String key = request.getParameter("key");
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        repositoryService.updateModel(id,name,desc);
        return new ResultMessage(0,"修改模型成功");
    }
    /**
     * 删除流程
    */
    @RequestMapping("deleteModel")
    public ResultMessage deleteModel(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        repositoryService.deleteModel(id);
        return new ResultMessage(0,"删除模型成功");
    }

    @RequestMapping("deploy/{modelId}")
    public ResultMessage deploy(@PathVariable String modelId){
        return repositoryService.deploy(modelId);
    }
}
