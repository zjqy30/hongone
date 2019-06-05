package com.hone.system.utils.suantao;

/**
 * Created by Lijia on 2019/5/29.
 * 酸桃RSA加密 封装类
 */
public class SuanTao {

    private String password;
    private Long platformId;
    private String identityCode;
    private Integer type;

    public SuanTao() {
    }

    public SuanTao(String password, long platformId, String identityCode, Integer type) {
        this.password = password;
        this.platformId = platformId;
        this.identityCode = identityCode;
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(long platformId) {
        this.platformId = platformId;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SuanTao{" +
                "password='" + password + '\'' +
                ", platformId='" + platformId + '\'' +
                ", identityCode='" + identityCode + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
