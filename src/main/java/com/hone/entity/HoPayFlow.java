package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author lijia
 * Date  2019-05-28
 * 平台交易流水，只记录交易成功
 */
@Table(name = "ho_pay_flow")
public class HoPayFlow extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String offerId;
    private String outTradeNo;//交易号
    private BigDecimal totalFee;//交易金额
    private String userId;
    private String status;//状态 1 成功 0 失败
    private String transType;//交易类型 PY 支付 DR 提现 RN 退款


    public HoPayFlow() {
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransType() {
        return transType;
    }


}