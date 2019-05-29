package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 */
@Table(name = "HoPayFlowHistory")
public class HoPayFlowHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String payFlowId;
    private String comments;


    public HoPayFlowHistory() {
    }


    public void setPayFlowId(String payFlowId) {
        this.payFlowId = payFlowId;
    }

    public String getPayFlowId() {
        return payFlowId;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }


}