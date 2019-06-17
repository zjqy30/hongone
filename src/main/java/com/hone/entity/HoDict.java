package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-05-30
 */
@Table(name = "ho_dict")
public class HoDict extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dictType;
    private String dictValue;
    private String dictDesc;
    private String dictSort;
    private String pid;
    private String dictPic;


    public HoDict() {
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictSort(String dictSort) {
        this.dictSort = dictSort;
    }

    public String getDictSort() {
        return dictSort;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public String getDictPic() {
        return dictPic;
    }

    public void setDictPic(String dictPic) {
        this.dictPic = dictPic;
    }
}