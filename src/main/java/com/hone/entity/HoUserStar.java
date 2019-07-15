package com.hone.entity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Author lijia
 * Date  2019-05-28
 * 网红扩展信息
 */
@Table(name = "ho_user_star")
public class HoUserStar extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private int fansNums;//粉丝数
    private String personalImgs;//私人照
    private int thumbUpNums;//点赞数
    private int workNums;//作品数
    private String platformUserId;//平台用户ID
    private String platformId;//平台ID
    private String platformImgs;//平台截图


    @Transient
    private String platformName;
    @Transient
    private String tags;
    @Transient
    private List<HoServiceTemplate> serviceTemplateList;


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


    public String getPlatformUserId() {
        return platformUserId;
    }

    public void setPlatformUserId(String platformUserId) {
        this.platformUserId = platformUserId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getPlatformImgs() {
        return platformImgs;
    }

    public void setPlatformImgs(String platformImgs) {
        this.platformImgs = platformImgs;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<HoServiceTemplate> getServiceTemplateList() {
        return serviceTemplateList;
    }

    public void setServiceTemplateList(List<HoServiceTemplate> serviceTemplateList) {
        this.serviceTemplateList = serviceTemplateList;
    }
}