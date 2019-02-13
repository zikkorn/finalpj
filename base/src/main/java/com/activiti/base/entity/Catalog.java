package com.activiti.base.entity;

import java.io.Serializable;

/**
 * 树目录
*/
public class Catalog implements Serializable{
    private static final long serialVersionUID = 1652137749324755480L;
    private String id;
    private String name="节点";//名称
    private String parentId;//父节点
    private String depict="节点";//描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }
}
