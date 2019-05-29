package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * banner图管理
 */
@Table(name = "HoBanners")
public class HoBanners extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imgs;//banner图片
    private String pages;//所在页面
    private String sort;//排序


    public HoBanners() {
    }


    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getImgs() {
        return imgs;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }


}