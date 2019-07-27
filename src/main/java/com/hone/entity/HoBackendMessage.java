package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-20
 */
@Table(name = "ho_backend_message")
public class HoBackendMessage extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content;
    private String type; //1 网红审核 2 商家审核 3 退款审核 4 提现审核 5 非纯佣订单审核 6 纯佣订单审核
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public HoBackendMessage() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}