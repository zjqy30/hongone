package com.hone.pc.backend.controller;

import com.hone.pc.backend.service.ApplayWithDrawService;
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
@RequestMapping("/backend/withDraw")
public class ApplayWithDrawController {

    private static Logger logger= LoggerFactory.getLogger(ApplayWithDrawController.class);

    @Autowired
    private ApplayWithDrawService applayWithDrawService;


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("申请提现列表");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=applayWithDrawService.list(params);
        }catch (Exception e){
            logger.error("申请提现列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/operate")
    public JsonResult operate(@RequestBody Map<String,String> params){
        logger.info("提现操作");
        JsonResult jsonResult=new JsonResult();
        //token校验
        if(JwtTokenUtils.checkToken(params)==false){
            jsonResult.loginExpire();
            return jsonResult;
        }
        try {
            jsonResult=applayWithDrawService.operate(params);
        }catch (Exception e){
            logger.error("提现操作",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
