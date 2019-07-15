package com.hone.applet.repo;

import java.util.List;

/**
 * Created by Lijia on 2019/7/9.
 */
public class HoSellerTagRepo {

    private String id;
    private String name;
    private String level;
    private String pid;

    private List<HoSellerTagRepo> childsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<HoSellerTagRepo> getChildsList() {
        return childsList;
    }

    public void setChildsList(List<HoSellerTagRepo> childsList) {
        this.childsList = childsList;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
