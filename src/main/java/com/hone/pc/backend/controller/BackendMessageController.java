package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.ApplayRefundService;
import com.hone.pc.backend.service.BackendMessageService;
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
@RequestMapping("/backend/backendMsg")
public class BackendMessageController {

    private static Logger logger= LoggerFactory.getLogger(BackendMessageController.class);

    @Autowired
    private BackendMessageService backendMessageService;


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("后台消息列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=backendMessageService.list(params);
        }catch (Exception e){
            logger.error("后台消息列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }




}
