package com.activiti.mapper;

import com.activiti.base.entity.Catalog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogMapper {
    /**
     * 查询树列表
     */
    List<Catalog> findList(Catalog catalog);
    /**
     * 新增目录
     */
    void addCatalog(Catalog catalog);
    /**
     *删除目录
     */
    void deleteCatalog(Catalog catalog);
    /**
     * 更新目录
    */
    void updateCatalog(Catalog catalog);

    /**
     *创建流程与目录的关联
    */
    void addCatalogModel(@Param("id") String id,@Param("catalogId") String catalogId,@Param("modelId") String modelId);

    /**
     * 删除流程的时候删除关联表
    */
    void  deleteCatalogModel(String id);

    /**
     * 分页查询目录列表
     */
    List<Catalog> findListByPage(Catalog catalog);
}
