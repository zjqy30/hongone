package com.hone.pc.backend.repo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by Lijia on 2019/7/12.
 */
public class ApplyWithDrawListRepo {

    private String id;
    private String wxName;
    private String userName;
    private String drawFee;
    private String drawAmount;
    private String cradBank;
    private String cradNumber;


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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDrawFee() {
        return drawFee;
    }

    public void setDrawFee(String drawFee) {
        this.drawFee = drawFee;
    }

    public String getDrawAmount() {
        return drawAmount;
    }

    public void setDrawAmount(String drawAmount) {
        this.drawAmount = drawAmount;
    }

    public String getCradBank() {
        return cradBank;
    }

    public void setCradBank(String cradBank) {
        this.cradBank = cradBank;
    }

    public String getCradNumber() {
        return cradNumber;
    }

    public void setCradNumber(String cradNumber) {
        this.cradNumber = cradNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
