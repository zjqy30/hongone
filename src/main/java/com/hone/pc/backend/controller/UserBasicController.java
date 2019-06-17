package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.UserBasicService;
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
@RequestMapping("/backend/userBasic")
public class UserBasicController {

    private static Logger logger= LoggerFactory.getLogger(UserBasicController.class);


    @Autowired
    private UserBasicService userBasicService;

    @RequestMapping("/star/list")
    public JsonResult starList(@RequestBody Map<String,String> params){
        logger.info("网红列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
             jsonResult.loginExpire();
             return jsonResult;
        }
        try {
            jsonResult=userBasicService.starList(params);
        }catch (Exception e){
            logger.error("网红列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/delUser")
    public JsonResult delUser(@RequestBody Map<String,String> params){
        logger.info("删除用户");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.delUser(params);
        }catch (Exception e){
            logger.error("删除用户",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/starInfo")
    public JsonResult starInfo(@RequestBody Map<String,String> params){
        logger.info("网红详情");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.starInfo(params);
        }catch (Exception e){
            logger.error("网红详情",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/starApproveList")
    public JsonResult starApproveList(@RequestBody Map<String,String> params){
        logger.info("网红待审核列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.starApproveList(params);
        }catch (Exception e){
            logger.error("网红待审核列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/starApproveOperate")
    public JsonResult starApproveOperate(@RequestBody Map<String,String> params){
        logger.info("网红审核操作");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.starApproveOperate(params);
        }catch (Exception e){
            logger.error("网红审核操作",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/sellerApproveList")
    public JsonResult sellerApproveList(@RequestBody Map<String,String> params){
        logger.info("商家待审核列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.sellerApproveList(params);
        }catch (Exception e){
            logger.error("商家待审核列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/sellerApproveOperate")
    public JsonResult sellerApproveOperate(@RequestBody Map<String,String> params){
        logger.info("商家审核操作");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.sellerApproveOperate(params);
        }catch (Exception e){
            logger.error("商家审核操作",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/seller/list")
    public JsonResult sellerList(@RequestBody Map<String,String> params){
        logger.info("商家列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.sellerList(params);
        }catch (Exception e){
            logger.error("商家列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/sellerInfo")
    public JsonResult sellerInfo(@RequestBody Map<String,String> params){
        logger.info("商家详情");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=userBasicService.sellerInfo(params);
        }catch (Exception e){
            logger.error("商家详情",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
