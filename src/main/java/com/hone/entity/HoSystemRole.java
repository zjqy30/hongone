package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-28
 * 用户角色
 */
@Table(name = "ho_system_role")
public class HoSystemRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String roleName;//角色名称
    private String page;//页面路径


    public HoSystemRole() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }


}