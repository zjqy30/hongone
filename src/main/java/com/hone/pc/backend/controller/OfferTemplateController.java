package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.OfferTemplateService;
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
@RequestMapping("/backend/offerTemplate")
public class OfferTemplateController {

    private static Logger logger= LoggerFactory.getLogger(OfferTemplateController.class);

    @Autowired
    private OfferTemplateService offerTemplateService;



    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("需求模板列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerTemplateService.list(params);
        }catch (Exception e){
            logger.error("需求模板列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("删除需求模板");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerTemplateService.del(params);
        }catch (Exception e){
            logger.error("删除需求模板",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params){
        logger.info("新建需求模板");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=offerTemplateService.create(params);
        }catch (Exception e){
            logger.error("新建需求模板",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
