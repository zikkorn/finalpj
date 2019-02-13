package com.activiti.base.entity.common;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 **/
public class PageResult extends ResultMessage implements Serializable{
    private static final long serialVersionUID = -6405912633144894538L;
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public PageResult() {
    }

    public PageResult(int code, String msg) {
        super(code, msg);
    }

    public <T> PageResult(int code, String msg, List<T> data) {
        super(code, msg, data);
        PageInfo<T> page = new PageInfo<T>(data);
        this.count = page.getTotal();
    }
}
