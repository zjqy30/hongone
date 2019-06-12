package com.hone.applet.controller;

import com.hone.applet.service.HoAccountBalanceService;
import com.hone.applet.service.HoAccountChargeService;
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
@RequestMapping("/applet/accountCharge")
public class HoAccountChargeController {

    private static Logger logger= LoggerFactory.getLogger(HoAccountChargeController.class);


    @Autowired
    private HoAccountChargeService hoAccountChargeService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoAccountChargeService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/list")
    public JsonResult list(@RequestBody Map<String,String> params){
        logger.info("账户变动记录");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoAccountChargeService.list(params);
        }catch (Exception e){
            logger.error("账户变动记录",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }





}
