package com.hone.pc.www.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hone.entity.HoSmsRecords;
import com.hone.entity.HoWebsiteMessage;
import com.hone.pc.www.service.HoWebsiteMessageService;
import com.hone.service.HoSmsRecordsService;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.SmsUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by Lijia on 2019/5/20.
 */

@RestController
@RequestMapping("/pc/website/message")
public class HoWebsiteMessageController {

    private static Logger logger= LoggerFactory.getLogger(HoWebsiteMessageController.class);

    @Autowired
    private HoWebsiteMessageService websiteMessageService;

    @Autowired
    private SmsUtils smsUtils;


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){

        logger.info("===message.list======");
        logger.info("-----------");

        String[] params_=new String[]{"1234"};
        String[] phoneNumbers=new String[]{"18261732399"};
        smsUtils.sendSms(params_,phoneNumbers,290650);

        JsonResult jsonResult=new JsonResult();

        List<HoWebsiteMessage> messageList= websiteMessageService.list();

        jsonResult.globalSuccess();
        jsonResult.getData().put("messageList",messageList);

        return jsonResult;
    }


    @RequestMapping("/sendSms")
    public JsonResult sendSms(@RequestBody Map<String,String> params){
        logger.info("发送网站短信验证码");
        JsonResult jsonResult=new JsonResult();

        try {
            String phoneNo=params.get("phoneNo");
            if(StringUtils.isEmpty(phoneNo)){
                jsonResult.globalError("手机号不能为空");
                return jsonResult;
            }

            if(!StringUtils.isNumeric(phoneNo)||phoneNo.length()!=11){
                jsonResult.globalError("手机号格式不正确");
                return jsonResult;
            }

            //数字验证码4位
            String verifyCode = RandomStringUtils.random(4, "1234567890");

            String[] params_=new String[]{verifyCode};
            String[] phoneNumbers=new String[]{phoneNo};
            boolean flag=smsUtils.sendSms(params_,phoneNumbers,290650);
            //验证码发送失败
            if(!flag){
                jsonResult.globalError("验证码发送失败");
                return jsonResult;
            }
        }catch (Exception e){
            logger.error("发送网站短信验证码error",e);
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }

    @RequestMapping("/saveMsg")
    public JsonResult saveMsg(@RequestBody Map<String,String> params){
        logger.info("网站留言");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=websiteMessageService.save(params);
        }catch (Exception e){
            logger.error("网站留言error",e);
        }

        return jsonResult;
    }

}
