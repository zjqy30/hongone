package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.SystemNoticeService;
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
@RequestMapping("/backend/sysNotice")
public class SystemNoticeController {

    private static Logger logger= LoggerFactory.getLogger(SystemNoticeController.class);

    @Autowired
    private SystemNoticeService systemNoticeService;



    @RequestMapping("/list")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("系统公告列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=systemNoticeService.list(params);
        }catch (Exception e){
            logger.error("系统公告列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/del")
    public JsonResult del(@RequestBody Map<String,String> params){
        logger.info("删除系统公告");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=systemNoticeService.del(params);
        }catch (Exception e){
            logger.error("删除系统公告",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }




    @RequestMapping("/create")
    public JsonResult create(@RequestBody Map<String,String> params){
        logger.info("新建系统公告");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=systemNoticeService.create(params);
        }catch (Exception e){
            logger.error("新建系统公告",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
