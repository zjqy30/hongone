package com.hone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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
    private double price;//价格
    private String remarks;//备注
    private String status;//状态
    private String offerTemplateId;
    private String userId;
    private String orderNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveDate;//审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockDate;//锁单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finshDate;//结束时间

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    public Date getFinshDate() {
        return finshDate;
    }

    public void setFinshDate(Date finshDate) {
        this.finshDate = finshDate;
    }
}