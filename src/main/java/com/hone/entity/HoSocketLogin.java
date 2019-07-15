package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-08
 */
@Table(name = "ho_socket_login")
public class HoSocketLogin extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String socketId;
    private String userId;


    public HoSocketLogin() {
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}