package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-08
 */
@Table(name = "ho_pure_offer")
public class HoPureOffer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String title;
    private String productTypeId;
    private String shopLevel;
    private String salesBefore;
    private String starPlate;
    private String fansNums;
    private String ifSend;
    private String profitRatio;
    private String status;


    public HoPureOffer() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setSalesBefore(String salesBefore) {
        this.salesBefore = salesBefore;
    }

    public String getSalesBefore() {
        return salesBefore;
    }

    public void setStarPlate(String starPlate) {
        this.starPlate = starPlate;
    }

    public String getStarPlate() {
        return starPlate;
    }

    public void setFansNums(String fansNums) {
        this.fansNums = fansNums;
    }

    public String getFansNums() {
        return fansNums;
    }

    public void setIfSend(String ifSend) {
        this.ifSend = ifSend;
    }

    public String getIfSend() {
        return ifSend;
    }

    public void setProfitRatio(String profitRatio) {
        this.profitRatio = profitRatio;
    }

    public String getProfitRatio() {
        return profitRatio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}