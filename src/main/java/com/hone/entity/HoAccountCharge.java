package com.hone.entity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author lijia
 * Date  2019-05-28
 * 用户账户余额变动记录
 */
@Table(name = "ho_account_charge")
public class HoAccountCharge extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String offerId;
    private String outTradeNo;//交易号
    private BigDecimal totalFee;//交易金额
    private BigDecimal serviceFee;//手续费
    private String chargeType;//交易类型
    private String chargeStatus;//交易状态
    private String userId;

    @Transient
    private String title;


    public HoAccountCharge() {
    }


    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}