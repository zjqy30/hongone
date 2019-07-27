package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 用户基本信息
 */
@Table(name = "ho_user_basic")
public class HoUserBasic extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String openId;
    private String wxName;//微信名
    private String userType;//用户类型 1 网红 2 商家
    private String ifApproved;//是否审核 -1 新用户 0 审核中 1 审核通过 2 审核拒绝
    private String idCardPic;//手持身份证照片
    private String idCardUpPic;//身份证正面照片
    private String idCardDownPic;//身份证反面照片
    private String marketerId;
    private String phoneNo;//手机号
    private String hasShop;//是否开店
    private String idCardNumber;//身份证号码
    private String encryptedData;//微信私密信息，解密后可获取手机号
    private String gender;//用户性别 1：男、2：女
    private String avatarUrl;//微信头像
    private String country;//所在城市
    private Integer age;//年龄
    private String personalIntroduce;//个人简介


    public HoUserBasic() {
    }


    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxName() {
        return wxName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setIfApproved(String ifApproved) {
        this.ifApproved = ifApproved;
    }

    public String getIfApproved() {
        return ifApproved;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardUpPic(String idCardUpPic) {
        this.idCardUpPic = idCardUpPic;
    }

    public String getIdCardUpPic() {
        return idCardUpPic;
    }

    public void setIdCardDownPic(String idCardDownPic) {
        this.idCardDownPic = idCardDownPic;
    }

    public String getIdCardDownPic() {
        return idCardDownPic;
    }

    public void setMarketerId(String marketerId) {
        this.marketerId = marketerId;
    }

    public String getMarketerId() {
        return marketerId;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setHasShop(String hasShop) {
        this.hasShop = hasShop;
    }

    public String getHasShop() {
        return hasShop;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPersonalIntroduce() {
        return personalIntroduce;
    }

    public void setPersonalIntroduce(String personalIntroduce) {
        this.personalIntroduce = personalIntroduce;
    }
}