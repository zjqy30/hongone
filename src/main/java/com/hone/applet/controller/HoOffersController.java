package com.hone.applet.controller;

import com.hone.applet.service.HoOffersService;
import com.hone.applet.service.HoWxFormidService;
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
@RequestMapping("/applet/offer")
public class HoOffersController {

    private static Logger logger= LoggerFactory.getLogger(HoOffersController.class);


    @Autowired
    private HoOffersService hoOffersService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("查看需求列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.list(params);
        }catch (Exception e){
            logger.error("查看需求列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/offerInfo")
    public JsonResult offerInfo(@RequestBody Map<String,String> params){
        logger.info("查看需求详情");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.offerInfo(params);
        }catch (Exception e){
            logger.error("查看需求详情",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/snatchOffer")
    public JsonResult snatchOffer(@RequestBody Map<String,String> params){
        logger.info("抢单");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.snatchOffer(params);
        }catch (Exception e){
            logger.error("抢单",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/ifSnatch")
    public JsonResult ifSnatch(@RequestBody Map<String,String> params){
        logger.info("查看是否抢单");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.ifSnatch(params);
        }catch (Exception e){
            logger.error("查看是否抢单",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }



}
