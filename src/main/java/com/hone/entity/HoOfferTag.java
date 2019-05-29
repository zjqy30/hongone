package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 需求-标签中间表
 */
@Table(name = "HoOfferTag")
public class HoOfferTag extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String offerId;
    private String dictId;


    public HoOfferTag() {
    }


    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictId() {
        return dictId;
    }

}