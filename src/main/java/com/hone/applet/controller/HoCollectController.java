package com.hone.applet.controller;

import com.hone.applet.service.HoCollectService;
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
@RequestMapping("/applet/collect")
public class HoCollectController {

    private static Logger logger= LoggerFactory.getLogger(HoCollectController.class);


    @Autowired
    private HoCollectService hoCollectService;


    @RequestMapping("/save")
    public JsonResult save(@RequestBody Map<String,String> params){
        logger.info("新增收藏");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoCollectService.edit(params);
        }catch (Exception e){
            logger.error("新增收藏",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/cancel")
    public JsonResult cancel(@RequestBody Map<String,String> params){
        logger.info("取消收藏");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoCollectService.cancel(params);
        }catch (Exception e){
            logger.error("取消收藏",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/ifCollect")
    public JsonResult ifCollect(@RequestBody Map<String,String> params){
        logger.info("查看是否收藏");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoCollectService.ifCollect(params);
        }catch (Exception e){
            logger.error("查看是否收藏",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("收藏列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoCollectService.list(params);
        }catch (Exception e){
            logger.error("收藏列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoCollectService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
