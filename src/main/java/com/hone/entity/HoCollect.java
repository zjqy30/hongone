package com.hone.entity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 我的收藏
 */
@Table(name = "ho_collect")
public class HoCollect extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String objectId;
    private String collectType;//收藏类型

    @Transient
    private String wxName;
    @Transient
    private String headPic;
    @Transient
    private String platName;
    @Transient
    private String platPic;
    @Transient
    private String tag;
    @Transient
    private String fansNums;
    @Transient
    private String thumbUpNums;
    @Transient
    private String gender;



    public HoCollect() {
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setCollectType(String collectType) {
        this.collectType = collectType;
    }

    public String getCollectType() {
        return collectType;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPlatPic() {
        return platPic;
    }

    public void setPlatPic(String platPic) {
        this.platPic = platPic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFansNums() {
        return fansNums;
    }

    public void setFansNums(String fansNums) {
        this.fansNums = fansNums;
    }

    public String getThumbUpNums() {
        return thumbUpNums;
    }

    public void setThumbUpNums(String thumbUpNums) {
        this.thumbUpNums = thumbUpNums;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}