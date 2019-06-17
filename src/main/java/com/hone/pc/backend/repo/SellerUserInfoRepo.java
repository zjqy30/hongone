package com.hone.pc.backend.repo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Lijia on 2019/6/13.
 */
public class SellerUserInfoRepo {

    private String id;
    private String wxName;//微信名
    private String age;
    private String sex;
    private String hasShop;//是否有店铺
    private String industry;//所属行业
    private String status;//用户状态
    private String marketer;//市场人员
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String country;//城市
    private String phoneNo;
    private String idCardPic;//手持身份证照片
    private String idCardUpPic;//身份证正面照片
    private String idCardDownPic;//身份证反面照片
    private String businessLicense;//营业执照
    private String idCardNumber;//身份证号
    private String industryId;


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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHasShop() {
        return hasShop;
    }

    public void setHasShop(String hasShop) {
        this.hasShop = hasShop;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarketer() {
        return marketer;
    }

    public void setMarketer(String marketer) {
        this.marketer = marketer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public String getIdCardUpPic() {
        return idCardUpPic;
    }

    public void setIdCardUpPic(String idCardUpPic) {
        this.idCardUpPic = idCardUpPic;
    }

    public String getIdCardDownPic() {
        return idCardDownPic;
    }

    public void setIdCardDownPic(String idCardDownPic) {
        this.idCardDownPic = idCardDownPic;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }
}
