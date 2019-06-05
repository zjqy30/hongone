package com.hone.applet.controller;

import com.hone.applet.service.HoBannerService;
import com.hone.applet.service.HoDictService;
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
@RequestMapping("/applet/banner")
public class HoBannerController {

    private static Logger logger= LoggerFactory.getLogger(HoBannerController.class);


    @Autowired
    private HoBannerService hoBannerService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoBannerService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("获取banner列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoBannerService.list(params);
        }catch (Exception e){
            logger.error("获取banner列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/save")
    public JsonResult save(@RequestBody Map<String,String> params){
        logger.info("保存banner");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoBannerService.save(params);
        }catch (Exception e){
            logger.error("保存banner",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
