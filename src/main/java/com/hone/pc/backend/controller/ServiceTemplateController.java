package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.OfferTemplateService;
import com.hone.pc.backend.service.ServiceTemplateService;
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
@RequestMapping("/backend/serviceTemplate")
public class ServiceTemplateController {

    private static Logger logger= LoggerFactory.getLogger(ServiceTemplateController.class);

    @Autowired
    private ServiceTemplateService serviceTemplateService;



    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("服务模板列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=serviceTemplateService.list(params);
        }catch (Exception e){
            logger.error("服务模板列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("删除服务模板");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=serviceTemplateService.del(params);
        }catch (Exception e){
            logger.error("删除服务模板",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params){
        logger.info("新建服务模板");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=serviceTemplateService.create(params);
        }catch (Exception e){
            logger.error("新建服务模板",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
