package com.activiti.service.impl;

import com.activiti.base.entity.Catalog;
import com.activiti.mapper.CatalogMapper;
import com.activiti.base.service.CatalogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 树目录service实现
 **/
@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CatalogMapper catalogMapper;

    @Override
    public List<Catalog> findList(Catalog catalog){
        return catalogMapper.findList(catalog);
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        if(null!=catalog.getId()&&!"".equals(catalog.getId())){
            catalogMapper.updateCatalog(catalog);
        }else{
            catalog.setId(UUID.randomUUID().toString());
            catalogMapper.addCatalog(catalog);
        }

    }

    @Override
    public void deleteCatalog(Catalog catalog) {
        catalogMapper.deleteCatalog(catalog);
    }

    @Override
    public void addCatalogModel(String catalogId,String modelId) {
        catalogMapper.addCatalogModel(UUID.randomUUID().toString(),catalogId,modelId);
    }

    /**
     * 分页查询目录列表
     */
    @Override
    public PageInfo<Catalog> findListByPage(Catalog catalog) {
        List<Catalog> list = catalogMapper.findListByPage(catalog);
        PageInfo<Catalog> result = new PageInfo<>(list);
        return result;
    }
}
