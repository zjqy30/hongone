package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 用户账户余额
 */
@Table(name = "HoAccountBalance")
public class HoAccountBalance extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String avaiableBalance;//可用余额


    public HoAccountBalance() {
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setAvaiableBalance(String avaiableBalance) {
        this.avaiableBalance = avaiableBalance;
    }

    public String getAvaiableBalance() {
        return avaiableBalance;
    }

}