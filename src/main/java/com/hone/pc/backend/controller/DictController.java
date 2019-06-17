package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.DictService;
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
@RequestMapping("/backend/dict")
public class DictController {

    private static Logger logger= LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictService dictService;



    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("标签列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=dictService.list(params);
        }catch (Exception e){
            logger.error("标签列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("删除标签");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=dictService.del(params);
        }catch (Exception e){
            logger.error("删除标签",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params){
        logger.info("新建标签");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=dictService.create(params);
        }catch (Exception e){
            logger.error("新建标签",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/updatePic")
    public JsonResult updatePic(@RequestBody Map<String,String> params){
        logger.info("标签更换图片");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=dictService.updatePic(params);
        }catch (Exception e){
            logger.error("标签更换图片",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/sellerTagList")
    public JsonResult sellerTagList(@RequestBody Map<String,String> params){
        logger.info("商家内幕列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=dictService.sellerTagList(params);
        }catch (Exception e){
            logger.error("商家内幕列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/createSellerTag")
    public JsonResult createSellerTag(@RequestBody Map<String,String> params){
        logger.info("新增商家内幕");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=dictService.createSellerTag(params);
        }catch (Exception e){
            logger.error("新增商家内幕",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
