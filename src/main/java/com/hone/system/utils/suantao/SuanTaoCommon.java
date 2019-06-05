package com.hone.system.utils.suantao;

/**
 * Created by Lijia on 2019/5/29.
 * 酸桃调用API 封装类
 */
public class SuanTaoCommon {

    private String userName;
    private String content;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SuanTaoCommon{" +
                "userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
