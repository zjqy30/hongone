package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.MarketerService;
import com.hone.pc.backend.service.SystemUserService;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/13.
 */

@RestController
@RequestMapping("/backend/marketer")
public class MarketerController {

    private static Logger logger= LoggerFactory.getLogger(MarketerController.class);

    @Autowired
    private MarketerService marketerService;



    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("销售人员列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=marketerService.list(params);
        }catch (Exception e){
            logger.error("销售人员列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("删除销售人员");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=marketerService.del(params);
        }catch (Exception e){
            logger.error("删除销售人员",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params, HttpServletRequest request){
        logger.info("创建销售人员");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=marketerService.create(params,request);
        }catch (Exception e){
            logger.error("创建销售人员",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/inviteList")
    public JsonResult inviteList(@RequestBody Map<String,String> params, HttpServletRequest request){
        logger.info("销售人员邀请列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=marketerService.inviteList(params,request);
        }catch (Exception e){
            logger.error("销售人员邀请列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }
}
