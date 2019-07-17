package com.hone.applet.repo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Lijia on 2019/6/3.
 */
public class HoOfferInfoRepo {

    private String headPic;
    private String wxName;
    private String shopPlate;
    private String starPlate;
    private String fanNums;
    private String offerTitle;
    private String tag;
    private String remarks;
    private String offerPic;
    private String status;
    private String ifSnatch;
    private String orderNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveDate;//发布时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lockDate;//锁单时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finshDate;//结束时间

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getShopPlate() {
        return shopPlate;
    }

    public void setShopPlate(String shopPlate) {
        this.shopPlate = shopPlate;
    }

    public String getStarPlate() {
        return starPlate;
    }

    public void setStarPlate(String starPlate) {
        this.starPlate = starPlate;
    }

    public String getFanNums() {
        return fanNums;
    }

    public void setFanNums(String fanNums) {
        this.fanNums = fanNums;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOfferPic() {
        return offerPic;
    }

    public void setOfferPic(String offerPic) {
        this.offerPic = offerPic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIfSnatch() {
        return ifSnatch;
    }

    public void setIfSnatch(String ifSnatch) {
        this.ifSnatch = ifSnatch;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
