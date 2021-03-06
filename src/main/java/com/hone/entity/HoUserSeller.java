package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 商家扩展信息
 */
@Table(name = "ho_user_seller")
public class HoUserSeller extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String businessLicense;//营业证书
    private String industryId;//所属行业 对应 dictId
    private String certLicense;//授权证书


    public HoUserSeller() {
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryId() {
        return industryId;
    }

    public String getCertLicense() {
        return certLicense;
    }

    public void setCertLicense(String certLicense) {
        this.certLicense = certLicense;
    }
}