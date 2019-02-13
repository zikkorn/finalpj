package com.tqy.cams.bean;

import java.util.List;

public class PageInfo {
    private int pageSize;  //每页显示条数
    private int totalSize;	//总条数
    private int pageNum;	//当前页数
    private List<?> list;		//数据
    
    public PageInfo() {
	}
    
	public PageInfo(int pageSize, int totalSize, int pageNum, List<?> list) {
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.pageNum = pageNum;
		this.list = list;
	}



	public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
