package com.hone.applet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hone.pc.www.controller.HoWebsiteMessageController;
import com.hone.system.utils.HttpUtils;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.ParamsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Lijia on 2019/5/29.
 */

@RestController
@RequestMapping("/applet/wx")
public class WxController {

    private static Logger logger= LoggerFactory.getLogger(WxController.class);

    @Value("${applet.appid}")
    private String appid;
    @Value("${applet.secret}")
    private String secret;

    @RequestMapping("/openId")
    public JsonResult getOpenId(@RequestBody Map<String,String> params){
        logger.info("获取微信openId");
        JsonResult jsonResult=new JsonResult();

        try {
            String code=params.get("code");
            ParamsUtil.checkParamIfNull(params,new String[]{"code"});

            String response = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", "appid=" + appid + "&secret="+secret+"&js_code=" + code + "&grant_type=authorization_code");
            System.out.println("获取openId返回信息："+response);
            JSONObject jsonObject=JSON.parseObject(response);
            if(StringUtils.isNotEmpty(jsonObject.getString("errcode"))&&jsonObject.getString("errcode").equals("40163")){
                jsonResult.globalError("code已经失效");
            }else {
                jsonResult.getData().put("openid", JSON.parseObject(response).getString("openid"));
                jsonResult.getData().put("session_key", JSON.parseObject(response).getString("session_key"));
                jsonResult.globalSuccess();
            }
        }catch (Exception e){
            logger.error("获取微信openId",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }

    public static void main(String[] args){
        System.out.println(String.format("www:%s:com","baidu"));//www.baidu.com
        System.out.println(String.format("www:%c:com",'a'));//www.a.com
        System.out.println(String.format("%+d",5));//+5
        System.out.println(String.format("%d",10/3));//3
        System.out.println(String.format("%03d",5));//005
        System.out.println(String.format("%,f",100000.0));//100,000.000000
    }

}
