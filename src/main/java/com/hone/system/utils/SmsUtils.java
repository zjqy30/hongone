package com.hone.system.utils;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.hone.entity.HoSmsRecords;
import com.hone.pc.www.controller.HoWebsiteMessageController;
import com.hone.service.HoSmsRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


/**
 * 腾讯云短信
 * Created by Lijia on 2019/5/20.
 */
@Component
@PropertySource(value = {"classpath:application.properties"})
public class SmsUtils {

    private static Logger logger= LoggerFactory.getLogger(SmsUtils.class);


    @Value("${tencent.sms.appid}")
    private  Integer appid;
    @Value("${tencent.sms.appkey}")
    private  String appkey;
    @Value("${tencent.sms.smsSign}")
    private  String smsSign;

    @Autowired
    private HoSmsRecordsService smsRecordsService;


    /**
     * 根据模板单发短信
     * @param params       短信参数
     * @param phoneNumbers 手机号码
     * @param templateId    模板ID
     * @return true/false 发送成功/失败
     */
    public  boolean sendSms(String[] params,String[] phoneNumbers,int templateId){
        boolean flag=true;
        try {
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (Exception e) {
            logger.error("短信验证码error:",e);
            flag=false;
        }

        if(flag){
            //更新之前验证码为失效
            smsRecordsService.delByPhoneNo(phoneNumbers[0]);

            //验证码发送成功
            HoSmsRecords smsRecords=new HoSmsRecords();
            smsRecords.setCode(params[0]);
            smsRecords.setPhoneNo(phoneNumbers[0]);
            smsRecords.preInsert();
            //过期时间
            Date createDate=smsRecords.getCreateDate();
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(createDate);
            calendar.add(Calendar.MINUTE,3);
            smsRecords.setExpireDate(calendar.getTime());
            smsRecordsService.save(smsRecords);
        }

        return  flag;
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
