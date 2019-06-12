package com.hone.applet.controller;

import com.hone.applet.service.HoAccountBalanceService;
import com.hone.applet.service.HoApplyWithdrawService;
import com.hone.dao.HoAccountBalanceDao;
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
@RequestMapping("/applet/accountBalance")
public class HoAccountBalanceController {

    private static Logger logger= LoggerFactory.getLogger(HoAccountBalanceController.class);


    @Autowired
    private HoAccountBalanceService hoAccountBalanceService;


    @RequestMapping("/initData")
    public JsonResult initData(@RequestBody Map<String,String> params){
        logger.info("初始化数据");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoAccountBalanceService.initData(params);
        }catch (Exception e){
            logger.error("初始化数据",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    @RequestMapping("/findByUser")
    public JsonResult accoutBalance(@RequestBody Map<String,String> params){
        logger.info("账户余额");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=hoAccountBalanceService.accoutBalance(params);
        }catch (Exception e){
            logger.error("账户余额",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
