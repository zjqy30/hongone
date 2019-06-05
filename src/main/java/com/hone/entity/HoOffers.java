package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 商家需求
 */
@Table(name = "ho_offers")
public class HoOffers extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int fansNum;//粉丝数
    private String gender;//性别
    private String imgs;//图片
    private String shopPlate;//卖家平台
    private String title;//标题
    private String userPlate;//网红平台
    private int price;//价格
    private String remarks;//备注
    private String status;//状态
    private String offerTemplateId;
    private String userId;


    public HoOffers() {
    }


    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getImgs() {
        return imgs;
    }

    public void setShopPlate(String shopPlate) {
        this.shopPlate = shopPlate;
    }

    public String getShopPlate() {
        return shopPlate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUserPlate(String userPlate) {
        this.userPlate = userPlate;
    }

    public String getUserPlate() {
        return userPlate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setOfferTemplateId(String offerTemplateId) {
        this.offerTemplateId = offerTemplateId;
    }

    public String getOfferTemplateId() {
        return offerTemplateId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


}