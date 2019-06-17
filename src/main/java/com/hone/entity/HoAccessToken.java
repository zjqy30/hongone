package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Author lijia
 * Date  2019-06-14
 */
@Table(name = "Ho_access_token")
public class HoAccessToken extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private Date expireDate;


    public HoAccessToken() {
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}