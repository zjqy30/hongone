package com.hone.pc.web.repo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Lijia on 2019/7/8.
 */
public class PureOfferListRepo {

    private String id;
    private String title;
    private String productType;
    private String fansNums;
    private String starPlate;
    private String profitRatio;//佣金比例
    private String starTag;
    private String shopLevel;
    private String ifSend;
    private String salesBefore;//以往销量

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getFansNums() {
        return fansNums;
    }

    public void setFansNums(String fansNums) {
        this.fansNums = fansNums;
    }

    public String getStarPlate() {
        return starPlate;
    }

    public void setStarPlate(String starPlate) {
        this.starPlate = starPlate;
    }

    public String getProfitRatio() {
        return profitRatio;
    }

    public void setProfitRatio(String profitRatio) {
        this.profitRatio = profitRatio;
    }

    public String getStarTag() {
        return starTag;
    }

    public void setStarTag(String starTag) {
        this.starTag = starTag;
    }

    public String getShopLevel() {
        return shopLevel;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getIfSend() {
        return ifSend;
    }

    public void setIfSend(String ifSend) {
        this.ifSend = ifSend;
    }

    public String getSalesBefore() {
        return salesBefore;
    }

    public void setSalesBefore(String salesBefore) {
        this.salesBefore = salesBefore;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
