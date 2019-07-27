package com.hone.applet.repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijia on 2019/6/6.
 */
public class HoSellerOfferListRepo {

    private String id;
    private String headPic;
    private String wxName;
    private String title;
    private String pic;
    private String tags;
    private String fansNum;
    private String status;
    private String snatchNums;
    private String phoneNo;
    private String remarks;
    private String sellApprove;
    private String starApprove;
    private String price;


    private List<String> starIds=new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFansNum() {
        return fansNum;
    }

    public void setFansNum(String fansNum) {
        this.fansNum = fansNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSnatchNums() {
        return snatchNums;
    }

    public void setSnatchNums(String snatchNums) {
        this.snatchNums = snatchNums;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public List<String> getStarIds() {
        return starIds;
    }

    public void setStarIds(List<String> starIds) {
        this.starIds = starIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSellApprove() {
        return sellApprove;
    }

    public void setSellApprove(String sellApprove) {
        this.sellApprove = sellApprove;
    }

    public String getStarApprove() {
        return starApprove;
    }

    public void setStarApprove(String starApprove) {
        this.starApprove = starApprove;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
