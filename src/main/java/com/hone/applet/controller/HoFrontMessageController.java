package com.hone.applet.controller;

import com.hone.applet.service.HoCommonService;
import com.hone.applet.service.HoFrontMessageService;
import com.hone.system.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/7/9.
 */

@RestController
@RequestMapping("/applet/frontMsg")
public class HoFrontMessageController {

    private static Logger logger= LoggerFactory.getLogger(HoFrontMessageController.class);

    @Autowired
    private HoFrontMessageService hoFrontMessageService;

    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("小程序端消息列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoFrontMessageService.list(params);
        }catch (Exception e){
            logger.error("小程序端消息列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
