package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author lijia
 * Date  2019-05-28
 * 提现申请记录
 */
@Table(name = "ho_apply_withdraw")
public class HoApplyWithdraw extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal drawAmount;//申请提现金额
    private BigDecimal drawFee;//手续费
    private String cradBank;//银行名称
    private String userName;//收款人
    private String cradNumber;//银行卡号
    private String status;//状态
    private String transferImgs;//转账截图
    private String userId;
    private String accountChargeId;


    public HoApplyWithdraw() {
    }


    public void setDrawAmount(BigDecimal drawAmount) {
        this.drawAmount = drawAmount;
    }

    public BigDecimal getDrawAmount() {
        return drawAmount;
    }

    public void setDrawFee(BigDecimal drawFee) {
        this.drawFee = drawFee;
    }

    public BigDecimal getDrawFee() {
        return drawFee;
    }

    public void setCradBank(String cradBank) {
        this.cradBank = cradBank;
    }

    public String getCradBank() {
        return cradBank;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCradNumber(String cradNumber) {
        this.cradNumber = cradNumber;
    }

    public String getCradNumber() {
        return cradNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTransferImgs(String transferImgs) {
        this.transferImgs = transferImgs;
    }

    public String getTransferImgs() {
        return transferImgs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountChargeId() {
        return accountChargeId;
    }

    public void setAccountChargeId(String accountChargeId) {
        this.accountChargeId = accountChargeId;
    }
}