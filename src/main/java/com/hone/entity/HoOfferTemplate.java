package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 需求模板
 */
@Table(name = "ho_offer_template")
public class HoOfferTemplate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;//标题
    private String infos;//描述
    private String minPrice;//最低价格
    private String maxPrice;//最高价格


    public HoOfferTemplate() {
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

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }


}