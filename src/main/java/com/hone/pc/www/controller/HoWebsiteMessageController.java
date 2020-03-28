package com.hone.pc.www.controller;

import com.hone.pc.www.service.HoWebsiteMessageService;
import com.hone.system.utils.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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


    // http://192.168.0.149:8081/pc/website/message/list
    @RequestMapping("/list")
    public JsonResult list(){

        logger.info("===message.list======");
        logger.info("-----------");

        JsonResult jsonResult=new JsonResult();


       // Page page= websiteMessageService.list();

        jsonResult.globalSuccess();
        //jsonResult.getData().put("page",page);

        return jsonResult;
    }


    @RequestMapping("/sendSms")
    public JsonResult sendSms(@RequestBody Map<String,String> params){
        logger.info("发送短信验证码");
        JsonResult jsonResult=new JsonResult();

        try {
            String phoneNo=params.get("phoneNo");
            String smsSign=params.get("smsSign");
            String type=params.get("type");
            ParamsUtil.checkParamIfNull(params,new String[]{"phoneNo","smsSign"});

            if(!StringUtils.isNumeric(phoneNo)||phoneNo.length()!=11){
                jsonResult.globalError("手机号格式不正确");
                return jsonResult;
            }

            //校验签名
            String makeSmsSign=smsUtils.makeSmsSign(phoneNo);
            if(!makeSmsSign.equals(smsSign)){
                jsonResult.globalError("签名不正确");
                return jsonResult;
            }

            //数字验证码4位
            String verifyCode = RandomStringUtils.random(4, "1234567890");

            String[] params_=new String[]{verifyCode};
            String[] phoneNumbers=new String[]{phoneNo};
            boolean flag=smsUtils.sendSms(params_,phoneNumbers,290650,type);
            //验证码发送失败
            if(!flag){
                jsonResult.globalError("验证码发送失败");
                return jsonResult;
            }
            jsonResult.globalSuccess();
        }catch (Exception e){
            logger.error("发送短信验证码error",e);
            jsonResult.globalError(e.getMessage());
        }
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
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    /**
     * 导出网站留言手机号码
     *
     * select any_value(create_date) as '创建时间',phone_no as '手机号' from ho_website_message where create_date > '2019-06-14' group by phone_no
     *
     *
     */


}
