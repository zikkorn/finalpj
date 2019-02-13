package com.activiti.base.service;

import com.activiti.base.entity.Catalog;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CatalogService  {
    /**
     * 查询树列表
     */
    List<Catalog> findList(Catalog catalog);
    /**
     * 添加/修改目录
     */
    void updateCatalog(Catalog catalog);
    /**
     *删除目录
     */
    void deleteCatalog(Catalog catalog);

    /**
     *创建流程与目录的关联
    */
    void addCatalogModel(String catalogId, String modelId);

    /**
     * 分页查询目录列表
     * @param catalog
     */
    PageInfo<Catalog> findListByPage(Catalog catalog);
}
