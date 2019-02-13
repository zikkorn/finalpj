package com.activiti.controller;

import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.entity.Catalog;
import com.activiti.base.service.CatalogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 树目录controller
 *
 **/
@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;


    /**
     * 分页查询目录列表
     */
    @RequestMapping("queryList")
    public ResultMessage queryList(Catalog catalog){
        PageInfo<Catalog> list = catalogService.findListByPage(catalog);
        return new ResultMessage(0,"查询树目录成功",list);
    }

    /** 
     * 查询树列表
    */
    @RequestMapping("list")
    public ResultMessage findList(Catalog catalog){
        List<Catalog> list = catalogService.findList(catalog);
        return new ResultMessage(0,"查询树目录成功",list);
    }

    /**
     *删除目录
    */
    @RequestMapping("deleteCatalog")
    public ResultMessage deleteCatalog(Catalog catalog){
        catalogService.deleteCatalog(catalog);
        return new ResultMessage(0,"删除成功");
    }

    /**
     * 添加/修改目录
    */
    @RequestMapping("updateCatalog")
    public ResultMessage updateCatalog(Catalog catalog){
        catalogService.updateCatalog(catalog);
        return new ResultMessage(0,"更新成功");
    }

}
