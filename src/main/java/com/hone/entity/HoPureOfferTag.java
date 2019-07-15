package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-08
 */
@Table(name = "ho_pure_offer_tag")
public class HoPureOfferTag extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String pureOfferId;
    private String dictId;


    public HoPureOfferTag() {
    }

    public void setPureOfferId(String pureOfferId) {
        this.pureOfferId = pureOfferId;
    }

    public String getPureOfferId() {
        return pureOfferId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictId() {
        return dictId;
    }

}