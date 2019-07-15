package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-09
 */
@Table(name = "ho_star_service")
public class HoStarService extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String templateId;
    private String price;


    public HoStarService() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}