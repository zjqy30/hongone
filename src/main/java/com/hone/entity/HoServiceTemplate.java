package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-09
 */
@Table(name = "ho_service_template")
public class HoServiceTemplate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String infos;
    private int sort;


    public HoServiceTemplate() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public String getInfos() {
        return infos;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSort() {
        return sort;
    }

}