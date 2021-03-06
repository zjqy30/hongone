package com.hone.system.utils;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijia on 2019/4/28.
 */
public class Page<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer pageNumer=1;
    private Integer pageSize=10;
    private Long totalCount;

    private List<T> list=new ArrayList<>();

    public Page(Integer pageNumer, Integer pageSize, List<T> list) {
        this.pageNumer = pageNumer;
        this.pageSize = pageSize;
        this.list = list;
    }

    public Page() {
    }

    public Page(Integer pageNumer, Integer pageSize, Long totalCount, List<T> list) {
        this.pageNumer = pageNumer;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
    }

    public Page(PageInfo<T> pageInfo) {
        this.pageNumer = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalCount =pageInfo.getTotal();
        this.list = pageInfo.getList();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getPageNumer() {
        return pageNumer;
    }

    public void setPageNumer(Integer pageNumer) {
        this.pageNumer = pageNumer;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
