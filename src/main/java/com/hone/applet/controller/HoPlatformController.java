package com.hone.applet.controller;

import com.hone.applet.service.HoPlatformService;
import com.hone.applet.service.HoUserBasicService;
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
@RequestMapping("/applet/platform")
public class HoPlatformController {

    private static Logger logger= LoggerFactory.getLogger(HoPlatformController.class);


    @Autowired
    private HoPlatformService hoPlatformService;



    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("平台列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoPlatformService.list(params);
        }catch (Exception e){
            logger.error("平台列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/save")
    public JsonResult save(@RequestBody Map<String,String> params){
        logger.info("新增平台");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoPlatformService.save(params);
        }catch (Exception e){
            logger.error("新增平台",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
