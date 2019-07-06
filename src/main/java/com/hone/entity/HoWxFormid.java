package com.hone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Author lijia
 * Date  2019-05-28
 * 微信模板消息
 */
@Table(name = "ho_wx_formid")
public class HoWxFormid extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String formId;
    private String userId;
    private String openId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate;//失效时间


    public HoWxFormid() {
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormId() {
        return formId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}