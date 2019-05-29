package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 抢单记录
 */
@Table(name = "HoSnatchOffer")
public class HoSnatchOffer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String offerId;
    private String userId;
    private String ifSelect;//是否选中


    public HoSnatchOffer() {
    }


    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setIfSelect(String ifSelect) {
        this.ifSelect = ifSelect;
    }

    public String getIfSelect() {
        return ifSelect;
    }


}