package com.hone.applet.controller;

import com.hone.applet.service.HoBannerService;
import com.hone.applet.service.HoWxPayService;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.wxpay.IpKit;
import com.hone.system.utils.wxpay.XmltoJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lijia on 2019/5/29.
 */
@RestController
@RequestMapping("/applet/wxpay")
public class HoWxPayController {

    private static Logger logger= LoggerFactory.getLogger(HoWxPayController.class);


    @Autowired
    private HoWxPayService hoWxPayService;


    @RequestMapping("/callPay")
    public JsonResult callPay(@RequestBody Map<String,String> params, HttpServletRequest httpServletRequest){
        logger.info("唤醒微信支付");
        JsonResult jsonResult=new JsonResult();

        try {
            if(StringUtils.isEmpty(params.get("spbillCreateIp"))){
                params.put("spbillCreateIp", IpKit.getRealIp(httpServletRequest));
            }
            jsonResult=hoWxPayService.callPay(params);
        }catch (Exception e){
            logger.error("唤醒微信支付",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


    /**
     * 支付回调
     * @param params
     * @param
     * @return
     */
    @RequestMapping("/callBack")
    public JsonResult callBack(@RequestBody Map<String,String> params, HttpServletRequest request, HttpServletResponse response){
        logger.info("微信支付回调");
        JsonResult jsonResult=new JsonResult();

        String result="";

        try {

            BufferedReader reader = request.getReader();
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            request.getReader().close();
            String xmlString = sb.toString();
            logger.info("微信支付完成回调："+xmlString);
            // 解析结果存储在HashMap
            Map<String, String> map = new HashMap<String, String>();
            map = XmltoJsonUtil.xmlToMap(xmlString);
            String outTradeNo = map.get("out_trade_no");
            result=hoWxPayService.callBack(xmlString,response,map);
        }catch (Exception e){
            logger.error("微信支付回调",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

}
