package com.hone.pc.web.socket;

import com.hone.system.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Lijia on 2019/6/17.
 */

@RestController
@RequestMapping("/websocket/qrcode")
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    @RequestMapping("/scan")
    public JsonResult starList(@RequestBody Map<String,String> params){

        JsonResult jsonResult=new JsonResult();

        String userId=params.get("userId");
        String type=params.get("type");
        if(type.equals("1")){
            try {
                webSocketServer.sendInfo("success", userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                webSocketServer.sendInfo("这是服务器推送消息到指定用户", userId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        jsonResult.globalSuccess();
        return jsonResult;
    }

}
