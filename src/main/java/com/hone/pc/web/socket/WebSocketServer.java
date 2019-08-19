package com.hone.pc.web.socket;


import com.hone.dao.HoSocketLoginDao;
import com.hone.entity.HoSocketLogin;
import com.hone.system.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/web/websocket/{socketId}/{ip}")
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String socketId;//客户端自定义的ID
    private String ip;//客户端IP

    private  HoSocketLoginDao hoSocketLoginDao=null;
    private static ApplicationContext applicationContext;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("socketId") String socketId,@PathParam("ip") String ip) {
        //赋值当前对象
        this.session = session;
        this.socketId = socketId;
        this.ip=ip;
        //加入 webSocketSet 中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();

        //通知客户端连接成功
        try {
            JsonResult jsonResult=new JsonResult();
            jsonResult.globalSuccess();
            jsonResult.setErrorCode("1001");
            sendMessage(jsonResult.toString());
        } catch (IOException e) {
            log.error("websocket IO异常");
        }

        //插入数据表
        hoSocketLoginDao= (HoSocketLoginDao) applicationContext.getBean("hoSocketLoginDao");
        HoSocketLogin hoSocketLogin=new HoSocketLogin();
        hoSocketLogin.setSocketId(socketId);
        hoSocketLogin.preInsert();
        hoSocketLoginDao.insert(hoSocketLogin);

        log.info("有新窗口开始监听:" + socketId + ",当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("有一连接关闭！socketId="+this.socketId+"当前在线人数为" + getOnlineCount());
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + socketId + "的信息:" + message);
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket连接发生错误，socketId="+this.socketId,error);
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public void sendInfo(String message, @PathParam("socketId") String socketId) throws IOException {
        log.info("推送消息到窗口" + socketId + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (socketId == null) {
                    item.sendMessage(message);
                } else if (item.socketId.equals(socketId)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount = WebSocketServer.onlineCount + 1;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount = WebSocketServer.onlineCount - 1;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

}