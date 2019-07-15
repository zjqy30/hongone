package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.BannerService;
import com.hone.pc.backend.service.PureOfferService;
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
@RequestMapping("/backend/pureOffer")
public class PureOfferController {

    private static Logger logger= LoggerFactory.getLogger(PureOfferController.class);

    @Autowired
    private PureOfferService pureOfferService;


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
            jsonResult=pureOfferService.list(params);
        }catch (Exception e){
            logger.error("纯佣订单列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/finsh")
    public JsonResult finsh(@RequestBody Map<String,String> params){
        logger.info("纯佣订单结束");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=pureOfferService.finsh(params);
        }catch (Exception e){
            logger.error("纯佣订单结束",e);
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
            jsonResult=pureOfferService.del(params);
        }catch (Exception e){
            logger.error("纯佣订单删除",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
