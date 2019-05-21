package com.hone.system.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Lijia on 2019/4/28.
 */
public class JsonResult implements Serializable{

    private static final long serialVersionUID = 1L;

    private String errorCode;   //0 成功 1 失败
    private String msg;         //返回信息
    private Map<String,Object> data=new LinkedHashMap<>();   //封装返回信息

    public void globalSuccess(){
        errorCode="0";
        msg="操作成功";
    }

    public void globalSuccess(String msg){
        errorCode="0";
        msg=msg;
    }

    public void globalError(String msg){
        errorCode="1";
        msg=msg;
    }

    public void globalError(){
        errorCode="1";
        msg="系统异常";
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
