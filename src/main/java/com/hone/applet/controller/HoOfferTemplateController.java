package com.hone.applet.controller;

import com.hone.applet.service.HoMarketerService;
import com.hone.applet.service.HoOfferTemplateService;
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
@RequestMapping("/applet/offerTemplate")
public class HoOfferTemplateController {

    private static Logger logger= LoggerFactory.getLogger(HoOfferTemplateController.class);


    @Autowired
    private HoOfferTemplateService hoOfferTemplateService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOfferTemplateService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("模板列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOfferTemplateService.list(params);
        }catch (Exception e){
            logger.error("模板列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }



}
