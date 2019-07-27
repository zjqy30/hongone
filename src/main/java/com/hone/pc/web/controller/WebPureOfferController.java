package com.hone.pc.web.controller;

import com.hone.pc.web.service.WebPureOfferService;
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
 * Created by Lijia on 2019/7/8.
 */

@RestController
@RequestMapping("/web/pureOffer")
public class WebPureOfferController {

    private static Logger logger= LoggerFactory.getLogger(WebPureOfferController.class);

    @Autowired
    private WebPureOfferService webPureOfferService;

    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params){
        logger.info("发布纯佣订单");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.create(params);
        }catch (Exception e){
            logger.error("发布纯佣订单",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("纯佣订单列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.list(params);
        }catch (Exception e){
            logger.error("纯佣订单列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/selfList")
    public JsonResult selfList(@RequestBody Map<String,String> params){
        logger.info("商户纯佣订单管理列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.selfList(params);
        }catch (Exception e){
            logger.error("商户纯佣订单管理列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("纯佣订单删除");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.del(params);
        }catch (Exception e){
            logger.error("纯佣订单删除",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/snatch")
    public JsonResult snatch(@RequestBody Map<String,String> params){
        logger.info("纯佣订单抢单");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.snatch(params);
        }catch (Exception e){
            logger.error("纯佣订单抢单",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/snatchList")
    public JsonResult snatchList(@RequestBody Map<String,String> params){
        logger.info("网红纯佣订单抢单列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.snatchList(params);
        }catch (Exception e){
            logger.error("网红纯佣订单抢单列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/delSnatch")
    public JsonResult delSnatch(@RequestBody Map<String,String> params){
        logger.info("网红删除纯佣订单抢单记录");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.delSnatch(params);
        }catch (Exception e){
            logger.error("网红删除纯佣订单抢单记录",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/ifSnatch")
    public JsonResult ifSnatch(@RequestBody Map<String,String> params){
        logger.info("查看网红是否抢纯佣单");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=webPureOfferService.ifSnatch(params);
        }catch (Exception e){
            logger.error("查看网红是否抢纯佣单",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }
}