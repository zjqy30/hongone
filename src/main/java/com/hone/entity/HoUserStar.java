package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 网红扩展信息
 */
@Table(name = "HoUserStar")
public class HoUserStar extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private int fansNums;//粉丝数
    private String personalImgs;//私人照
    private int thumbUpNums;//点赞数
    private int workNums;//作品数
    private String platform;//平台
    private String platformIconid;//平台图标ID
    private String platformId;//平台ID


    public HoUserStar() {
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setFansNums(int fansNums) {
        this.fansNums = fansNums;
    }

    public int getFansNums() {
        return fansNums;
    }

    public void setPersonalImgs(String personalImgs) {
        this.personalImgs = personalImgs;
    }

    public String getPersonalImgs() {
        return personalImgs;
    }

    public void setThumbUpNums(int thumbUpNums) {
        this.thumbUpNums = thumbUpNums;
    }

    public int getThumbUpNums() {
        return thumbUpNums;
    }

    public void setWorkNums(int workNums) {
        this.workNums = workNums;
    }

    public int getWorkNums() {
        return workNums;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatformIconid(String platformIconid) {
        this.platformIconid = platformIconid;
    }

    public String getPlatformIconid() {
        return platformIconid;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformId() {
        return platformId;
    }


}