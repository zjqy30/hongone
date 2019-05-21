package com.hone.pc.www.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hone.entity.HoWebsiteMessage;
import com.hone.pc.www.service.HoWebsiteMessageService;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

}
