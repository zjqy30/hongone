package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.OfferService;
import com.hone.pc.backend.service.SystemNoticeService;
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
@RequestMapping("/backend/offer")
public class OfferController {

    private static Logger logger= LoggerFactory.getLogger(OfferController.class);

    @Autowired
    private OfferService offerService;


    @RequestMapping("/list")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("需求列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.list(params);
        }catch (Exception e){
            logger.error("需求列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }



    @RequestMapping("/approveOperate")
    public JsonResult approveOperate(@RequestBody Map<String,String> params){
        logger.info("需求审核操作");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.approveOperate(params);
        }catch (Exception e){
            logger.error("需求审核操作",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("派单中删除需求");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.del(params);
        }catch (Exception e){
            logger.error("派单中删除需求",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/snatchUserList")
    public JsonResult snatchUserList(@RequestBody Map<String,String> params){
        logger.info("抢单用户列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.snatchUserList(params);
        }catch (Exception e){
            logger.error("抢单用户列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/finsh")
    public JsonResult finsh(@RequestBody Map<String,String> params){
        logger.info("结束需求");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.finsh(params);
        }catch (Exception e){
            logger.error("结束需求",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/recover")
    public JsonResult recover(@RequestBody Map<String,String> params){
        logger.info("进行中需求恢复");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.recover(params);
        }catch (Exception e){
            logger.error("进行中需求恢复",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/autoCreate")
    public JsonResult autoCreate(@RequestBody Map<String,String> params){
        logger.info("订单生成");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerService.autoCreate(params);
        }catch (Exception e){
            logger.error("订单生成",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
