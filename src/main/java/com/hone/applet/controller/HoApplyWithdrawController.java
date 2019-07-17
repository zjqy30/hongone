package com.hone.applet.controller;

import com.hone.applet.service.HoApplyWithdrawService;
import com.hone.applet.service.HoCollectService;
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
@RequestMapping("/applet/applyWithDraw")
public class HoApplyWithdrawController {

    private static Logger logger= LoggerFactory.getLogger(HoApplyWithdrawController.class);


    @Autowired
    private HoApplyWithdrawService hoApplyWithdrawService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoApplyWithdrawService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/apply")
    public JsonResult apply(@RequestBody Map<String,String> params){
        logger.info("申请提现");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoApplyWithdrawService.apply(params);
        }catch (Exception e){
            logger.error("申请提现",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    @RequestMapping("/receiverList")
    public JsonResult receiverList(@RequestBody Map<String,String> params){
        logger.info("最近收款人列表");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoApplyWithdrawService.receiverList(params);
        }catch (Exception e){
            logger.error("最近收款人列表",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
