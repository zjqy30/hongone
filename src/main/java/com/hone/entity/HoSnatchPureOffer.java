package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-25
 */
@Table(name = "ho_snatch_pure_offer")
public class HoSnatchPureOffer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pureOfferId;
    private String userId;


    public HoSnatchPureOffer() {
    }

    public void setPureOfferId(String pureOfferId) {
        this.pureOfferId = pureOfferId;
    }

    public String getPureOfferId() {
        return pureOfferId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}