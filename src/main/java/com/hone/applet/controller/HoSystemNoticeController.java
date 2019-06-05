package com.hone.applet.controller;

import com.hone.applet.service.HoPayFlowService;
import com.hone.applet.service.HoSystemNoticeService;
import com.hone.system.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/5/29.
 */
@RestController
@RequestMapping("/applet/systemNotice")
public class HoSystemNoticeController {

    private static Logger logger= LoggerFactory.getLogger(HoSystemNoticeController.class);


    @Autowired
    private HoSystemNoticeService hoSystemNoticeService;


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("获取系统通知");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoSystemNoticeService.list(params);
        }catch (Exception e){
            logger.error("获取系统通知",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
