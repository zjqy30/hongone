package com.hone.pc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.hone.entity.HoSocketLogin;
import com.hone.entity.HoUserBasic;
import com.hone.pc.backend.controller.BannerController;
import com.hone.pc.web.service.WebSocketLoginService;
import com.hone.pc.web.service.WebUserBasicService;
import com.hone.pc.web.socket.WebSocketServer;
import com.hone.system.utils.DateUtils;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import com.hone.system.utils.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lijia on 2019/7/8.
 */
@RestController
@RequestMapping("/web/userBasic")
public class WebUserBasicController {

    private static Logger logger= LoggerFactory.getLogger(WebUserBasicController.class);

    @Autowired
    private  WebSocketServer webSocketServer;
    @Autowired
    private WebUserBasicService webUserBasicService;
    @Autowired
    private WebSocketLoginService webSocketLoginService;


    @RequestMapping("/scan")
    public void scan(@RequestBody Map<String,String> params){
        logger.info("扫码登录");
        JSONObject jsonObject=new JSONObject();
        String socketId="";
        String openId="";
        try {

            socketId=params.get("socketId");
            openId=params.get("openId");
            ParamsUtil.checkParamIfNull(params,new String[]{"socketId","openId"});

            //校验socketId有效性
            HoSocketLogin hoSocketLogin= webSocketLoginService.findBySocketId(socketId);
            if(hoSocketLogin==null){
                jsonObject.put("errorCode","1003");
                jsonObject.put("message","二维码已经失效");
                webSocketServer.sendInfo(jsonObject.toJSONString(),socketId);
                return;
            }

            //是否超过五分钟
            long timeSub=new Date().getTime()-hoSocketLogin.getCreateDate().getTime();
            System.out.println(timeSub/1000/60);
            if(timeSub/1000/60>=5){
                jsonObject.put("errorCode","1003");
                jsonObject.put("message","二维码已经失效");
                webSocketServer.sendInfo(jsonObject.toJSONString(),socketId);
                return;
            }

            //校验用户信息逻辑
            HoUserBasic hoUserBasic= webUserBasicService.findByOpenId(openId);
            if(hoUserBasic==null){
                jsonObject.put("errorCode","1003");
                jsonObject.put("message","用户不存在，请先扫码登录小程序");
                webSocketServer.sendInfo(jsonObject.toJSONString(),socketId);
                return;
            }
            //更新 WebSocketLogin
            hoSocketLogin.setEnableFlag("0");
            hoSocketLogin.setUserId(hoUserBasic.getId());
            webSocketLoginService.update(hoSocketLogin);

            //生成token
            String token=JwtTokenUtils.createToken(hoUserBasic.getId());

            //发送消息到客户端
            jsonObject.put("userId", hoUserBasic.getId());
            jsonObject.put("errorCode", "1002");
            jsonObject.put("message","操作成功");
            jsonObject.put("openid", hoUserBasic.getOpenId());
            jsonObject.put("userType", hoUserBasic.getUserType());
            jsonObject.put("ifApproved", hoUserBasic.getIfApproved());
            jsonObject.put("phoneNo", hoUserBasic.getPhoneNo());
            jsonObject.put("headPic", hoUserBasic.getAvatarUrl());
            jsonObject.put("wxName", hoUserBasic.getWxName());
            jsonObject.put("token", token);
            webSocketServer.sendInfo(jsonObject.toJSONString(),socketId);

        }catch (Exception e){
            logger.error("扫码登录",e);
            jsonObject.clear();
            jsonObject.put("errorCode", "1003");
            jsonObject.put("message","服务器故障稍后再试");
            try {
                webSocketServer.sendInfo(jsonObject.toJSONString(),socketId);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        String str="{errorCode='1002', msg='操作成功', data={userId=3b1af32bdef744eaafd99b728d12c74a, openid=o562H5ID7DnGiGjpiQOTSIDeyXNw, userType=2, ifApproved=1, phoneNo=18261732399, headPic=https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLIuzryYia3h8OHV6XbVIiahhTqfqgGiaib0Qez8byq3IR5iaicW55WEQ0kmQVXTHiaqULhKFRc5LYDqbicPw/132, token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjQxNzY4MDksInVzZXJJRCI6IjNiMWFmMzJiZGVmNzQ0ZWFhZmQ5OWI3MjhkMTJjNzRhIiwiaWF0IjoxNTY0MDQ3MjA5fQ.Kpti61_m5-lq2ahfwfrp1ib0ZXwEIq3YbVPf91kXdbg}}";
        System.out.println(JSONObject.toJSONString(str).toString());
    }


    @RequestMapping("/loginByPhone")
    public JsonResult loginByPhone(@RequestBody Map<String,String> params){
        logger.info("手机验证码登录");
        JsonResult jsonResult=new JsonResult();

        try {
            jsonResult=webUserBasicService.loginByPhone(params);
        }catch (Exception e){
            logger.error("手机验证码登录",e);
            jsonResult.globalError(e.getMessage());
        }

        return jsonResult;
    }


}
