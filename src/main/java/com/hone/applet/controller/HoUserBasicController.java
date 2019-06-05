package com.hone.applet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.applet.service.HoMarketerService;
import com.hone.applet.service.HoUserBasicService;
import com.hone.entity.HoMarketer;
import com.hone.entity.HoUserBasic;
import com.hone.system.utils.HttpUtils;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/29.
 */
@RestController
@RequestMapping("/applet/userBasic")
public class HoUserBasicController {

    private static Logger logger= LoggerFactory.getLogger(HoUserBasicController.class);


    @Autowired
    private HoUserBasicService hoUserBasicService;

    @RequestMapping("/login")
    public JsonResult login(@RequestBody Map<String,String> params){
        logger.info("微信用户登录");
        JsonResult jsonResult=new JsonResult();

        try {
           jsonResult=hoUserBasicService.login(params);
        }catch (Exception e){
            logger.error("微信用户登录",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/star/applyApproved")
    public JsonResult applyApproved(@RequestBody Map<String,String> params){
        logger.info("网红用户申请认证");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.applyApproved(params);
        }catch (Exception e){
            logger.error("网红用户申请认证",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }



    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/initDataStar")
    public JsonResult initDataStar(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.initDataStar(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/initDataSell")
    public JsonResult initDataSell(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.initDataSell(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/listByStar")
    public JsonResult listByStar(@RequestBody Map<String,String> params){
        logger.info("获取网红列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.listByStar(params);
        }catch (Exception e){
            logger.error("获取网红列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/userStarInfo")
    public JsonResult userStarInfo(@RequestBody Map<String,String> params){
        logger.info("查看网红用户详情");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.userStarInfo(params);
        }catch (Exception e){
            logger.error("查看网红用户详情",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/userStarSelfInfo")
    public JsonResult userStarSelfInfo(@RequestBody Map<String,String> params){
        logger.info("网红本人查看资料");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.userStarSelfInfo(params);
        }catch (Exception e){
            logger.error("网红本人查看资料",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/updateStarSelfInfo")
    public JsonResult updateStarSelfInfo(@RequestBody Map<String,String> params){
        logger.info("更新网红本人资料");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoUserBasicService.updateStarSelfInfo(params);
        }catch (Exception e){
            logger.error("更新网红本人资料",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
