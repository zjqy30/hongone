package com.hone.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by Lijia on 2019/5/20.
 */

@Table(name = "ho_website_message")
public class HoWebsiteMessage  extends BaseEntity  implements Serializable{

    private static final long serialVersionUID = 1L;

    private String message;
    private String phoneNo;

    @Transient
    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
