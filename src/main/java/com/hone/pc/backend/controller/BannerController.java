package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.BannerService;
import com.hone.pc.backend.service.SystemUserService;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/6/13.
 */

@RestController
@RequestMapping("/backend/banner")
public class BannerController {

    private static Logger logger= LoggerFactory.getLogger(BannerController.class);

    @Autowired
    private BannerService bannerService;



    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("轮播图列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=bannerService.list(params);
        }catch (Exception e){
            logger.error("轮播图列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params){
        logger.info("创建轮播图");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=bannerService.create(params);
        }catch (Exception e){
            logger.error("创建轮播图",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("删除轮播图");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=bannerService.del(params);
        }catch (Exception e){
            logger.error("删除轮播图",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
