package com.hone.pc.backend.repo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Lijia on 2019/7/12.
 */
public class PureOfferListRepo {

    private String id;
    private String wxName;
    private String goodsType;
    private String fansNums;
    private String platName;
    private String profitRatio;
    private String tags;
    private String shopLevel;
    private String ifSend;
    private String salesBefore;
    private String status;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getFansNums() {
        return fansNums;
    }

    public void setFansNums(String fansNums) {
        this.fansNums = fansNums;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getProfitRatio() {
        return profitRatio;
    }

    public void setProfitRatio(String profitRatio) {
        this.profitRatio = profitRatio;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
