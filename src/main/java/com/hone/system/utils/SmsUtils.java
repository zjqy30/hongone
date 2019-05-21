package com.hone.system.utils;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 腾讯云短信
 * Created by Lijia on 2019/5/20.
 */
@Component
@PropertySource(value = {"classpath:application.properties"})
public class SmsUtils {

    @Value("${tencent.sms.appid}")
    private  Integer appid;
    @Value("${tencent.sms.appkey}")
    private  String appkey;
    @Value("${tencent.sms.smsSign}")
    private  String smsSign;


    /**
     * 根据模板单发短信
     * @param params       短信参数
     * @param phoneNumbers 手机号码
     * @param templateId    模板ID
     * @return true/false 发送成功/失败
     */
    public  boolean sendSms(String[] params,String[] phoneNumbers,int templateId){
        try {
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }


    public  void main(String[] args){
//        String[] params=new String[]{"1234"};
//        String[] phoneNumbers=new String[]{"18261732399"};
//        boolean flag=sendSms(params,phoneNumbers,290650);
    }


    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getSmsSign() {
        return smsSign;
    }

    public void setSmsSign(String smsSign) {
        this.smsSign = smsSign;
    }
}
