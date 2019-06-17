package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 系统通知
 */
@Table(name = "ho_system_notice")
public class HoSystemNotice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;//通知内容

    public HoSystemNotice() {
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}