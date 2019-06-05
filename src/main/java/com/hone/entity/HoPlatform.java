package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-30
 */
@Table(name = "ho_platform")
public class HoPlatform extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String platformName;
    private String platformIcon;
    private String platformCode;


    public HoPlatform() {
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformIcon(String platformIcon) {
        this.platformIcon = platformIcon;
    }

    public String getPlatformIcon() {
        return platformIcon;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
}