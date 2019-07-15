package com.hone.pc.web.controller;

import com.hone.entity.HoSocketLogin;
import com.hone.entity.HoUserBasic;
import com.hone.pc.backend.controller.BannerController;
import com.hone.pc.web.service.WebSocketLoginService;
import com.hone.pc.web.service.WebUserBasicService;
import com.hone.pc.web.socket.WebSocketServer;
import com.hone.system.utils.JsonResult;
import com.hone.system.utils.JwtTokenUtils;
import com.hone.system.utils.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public JsonResult scan(@RequestBody Map<String,String> params){
        logger.info("扫码登录");
        JsonResult jsonResult=new JsonResult();
        try {

            String socketId=params.get("socketId");
            String openId=params.get("openId");
            ParamsUtil.checkParamIfNull(params,new String[]{"socketId","openId"});

            //校验socketId有效性
            HoSocketLogin hoSocketLogin= webSocketLoginService.findBySocketId(socketId);
            if(hoSocketLogin==null){
                jsonResult.globalError("二维码已经失效");
                return jsonResult;
            }
            //校验用户信息逻辑
            HoUserBasic hoUserBasic= webUserBasicService.findByOpenId(openId);
            if(hoUserBasic==null){
                jsonResult.globalError("用户不存在");
                return jsonResult;
            }
            //更新 WebSocketLogin
            hoSocketLogin.setEnableFlag("0");
            hoSocketLogin.setUserId(hoUserBasic.getId());
            webSocketLoginService.update(hoSocketLogin);

            //生成token
            String token=JwtTokenUtils.createToken(hoUserBasic.getId());

            //发送消息到客户端
            jsonResult.globalSuccess();
            jsonResult.setErrorCode("1002");
            jsonResult.getData().put("userId", hoUserBasic.getId());
            jsonResult.getData().put("openid", hoUserBasic.getOpenId());
            jsonResult.getData().put("userType", hoUserBasic.getUserType());
            jsonResult.getData().put("ifApproved", hoUserBasic.getIfApproved());
            jsonResult.getData().put("phoneNo", hoUserBasic.getPhoneNo());
            jsonResult.getData().put("headPic", hoUserBasic.getAvatarUrl());
            jsonResult.getData().put("token", token);
            webSocketServer.sendInfo(jsonResult.toString(),socketId);

        }catch (Exception e){
            logger.error("扫码登录",e);
            jsonResult.globalError(e.getMessage());
        }

        jsonResult.getData().clear();
        return jsonResult;
    }

}
