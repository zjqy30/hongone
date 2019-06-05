package com.hone.applet.controller;

import com.hone.applet.service.HoAccountChargeService;
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
@RequestMapping("/applet/dict")
public class HoDictController {

    private static Logger logger= LoggerFactory.getLogger(HoDictController.class);


    @Autowired
    private HoDictService hoDictService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoDictService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/save")
    public JsonResult save(@RequestBody Map<String,String> params){
        logger.info("新增字典表数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoDictService.save(params);
        }catch (Exception e){
            logger.error("新增字典表数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/listByType")
    public JsonResult listByType(@RequestBody Map<String,String> params){
        logger.info("根据类型获取字典表数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoDictService.listByType(params);
        }catch (Exception e){
            logger.error("根据类型获取字典表数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }



}
