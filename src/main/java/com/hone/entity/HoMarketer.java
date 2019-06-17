package com.hone.entity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 市场人员
 */
@Table(name = "ho_marketer")
public class HoMarketer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String marketName;//用户名
    private String openId;
    private String userCode;//用户编码->邀请码
    private String qrcodeUrl;//二维码地址


    @Transient
    private String inviteNums;//邀请人数


    public HoMarketer() {
    }


    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getInviteNums() {
        return inviteNums;
    }

    public void setInviteNums(String inviteNums) {
        this.inviteNums = inviteNums;
    }
}