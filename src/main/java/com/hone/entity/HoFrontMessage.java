package com.hone.entity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-24
 */
@Table(name = "ho_front_message")
public class HoFrontMessage extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private String objectId;
    private String type;//1 网红入驻 2 商家入驻 3 非纯佣订单发布 4 纯佣订单发布


    @Transient
    private String hoursDiff;//时间差


    public String getHoursDiff() {
        return hoursDiff;
    }

    public void setHoursDiff(String hoursDiff) {
        this.hoursDiff = hoursDiff;
    }

    public HoFrontMessage() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}