package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 网红用户技能
 */
@Table(name = "HoUserTag")
public class HoUserTag extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String dictId;//字典表ID


    public HoUserTag() {
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictId() {
        return dictId;
    }

}