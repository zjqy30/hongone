package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lijia on 2019/5/25.
 */
@Table(name = "ho_token")
public class HoToken  extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private String userId;

    private Date expireDate;

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
