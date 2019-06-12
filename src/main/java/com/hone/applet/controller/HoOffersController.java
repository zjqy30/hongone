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


    @RequestMapping("/starSnatchList")
    public JsonResult starSnatchList(@RequestBody Map<String,String> params){
        logger.info("网红抢单记录");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.starSnatchList(params);
        }catch (Exception e){
            logger.error("网红抢单记录",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/relase")
    public JsonResult relase(@RequestBody Map<String,String> params){
        logger.info("发布需求");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.relase(params);
        }catch (Exception e){
            logger.error("发布需求",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/sellerOfferList")
    public JsonResult sellerOfferList(@RequestBody Map<String,String> params){
        logger.info("商家查看自己需求列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.sellerOfferList(params);
        }catch (Exception e){
            logger.error("商家查看自己需求列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/applyRefund")
    public JsonResult applyRefund(@RequestBody Map<String,String> params){
        logger.info("申请退款");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.applyRefund(params);
        }catch (Exception e){
            logger.error("申请退款",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/confirmFN")
    public JsonResult confirmFN(@RequestBody Map<String,String> params){
        logger.info("商家确认订单完成");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.confirmFN(params);
        }catch (Exception e){
            logger.error("商家确认订单完成",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/lock")
    public JsonResult lock(@RequestBody Map<String,String> params){
        logger.info("商家锁单");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoOffersService.lock(params);
        }catch (Exception e){
            logger.error("商家锁单",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
