package com.hone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hone.system.utils.IdUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Lijia on 2019/5/22.
 */
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;//更新时间
    private String enableFlag;//有效位 1 有效 0 无效

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public void preInsert(){
        this.id= IdUtils.uuid();
        Date now=new Date();
        this.createDate=now;
        this.updateDate=now;
        this.enableFlag="1";
    }

}
