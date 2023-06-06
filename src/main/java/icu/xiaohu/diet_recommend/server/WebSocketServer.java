package icu.xiaohu.diet_recommend.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import icu.xiaohu.diet_recommend.model.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohu
 * @date 2023/06/02/ 14:48
 * @description
 */
@Component
@Slf4j
@ServerEndpoint("/api/pushMessage/{userId}/{admin}")
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static volatile int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     */
    private static ConcurrentHashMap<Long, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 管理员连接单独维护
     */
    private static ConcurrentHashMap<Long, WebSocketServer> adminWebSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收 userId 用于区别
     */
    private Long userId;


    /**
     * 发送自定义消息
     **/
    public static void sendInfo(Message message, Long toUserId) {
        log.info("发送消息到:" + toUserId + "，报文:" + message);
        if (webSocketMap.containsKey(toUserId)) {
            JSONObject json = (JSONObject) JSON.toJSON(message);
            webSocketMap.get(toUserId).sendMessage(json.toJSONString());
        } else {
            log.error("用户" + toUserId + ",不在线！");
            // TODO 将消息保存数据库

        }
    }

    /**
     * 发起审核
     **/
    public static void sendCheck(String message, Long byUserId) {
        log.info("用户"+ byUserId +"发起审核推送");
        // 管理员是否在线
        if (adminWebSocketMap.isEmpty()) {

        }
        // 直接遍历管理员会话
        for (Map.Entry<Long, WebSocketServer> admin : adminWebSocketMap.entrySet()) {
            admin.getValue().sendMessage(message);
        }
    }

    /**
     * 发送自定义实体消息
     **/
    public static void sendStatusInfo(Object message, String userId) {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).sendStatusInfo(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("admin") String admin, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
        // 判断管理员
        if ("admin".equals(admin)){
            if (adminWebSocketMap.containsKey(userId)) {
                adminWebSocketMap.remove(userId);
                // 加入map中
                adminWebSocketMap.put(userId, this);
            } else {
                // 加入map中
                adminWebSocketMap.put(userId, this);
                // 在线数加1
                addOnlineCount();
            }
        }else{
            if (webSocketMap.containsKey(userId)) {
                webSocketMap.remove(userId);
                // 加入map中
                webSocketMap.put(userId, this);
            } else {
                // 加入map中
                webSocketMap.put(userId, this);
                // 在线数加1
                addOnlineCount();
            }
        }

        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        sendMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            //从 map 中删除
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param data 客户端发送过来的消息
     **/
    @OnMessage
    public void onMessage(String data, Session session) {
        log.info("用户消息:" + userId + ",报文:" + data);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(data)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(data);
                //追加发送人(防止串改)
                jsonObject.put("fromuserId", this.userId);
                String touserId = jsonObject.getString("touserId");
                String message = jsonObject.getString("message");
                //传送给对应touserId鸽笼的websocket
                if (webSocketMap.containsKey(touserId)) {
                    //业务
                    webSocketMap.get(touserId).sendMessage(message);
                } else {
                    //userId不存在
                    // TODO 将消息保存在数据库，等用户上线再推送
                    log.error("请求的userId:" + touserId + "不在该服务器上");
                    webSocketMap.get(this.userId).sendMessage("请求的userId:" + touserId + "不在该服务器上");
                }
            } catch (Exception e) {
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

        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) {
        try {
            log.info("--------------websocket主动推送数据：{}", message);
            synchronized (session) {
                this.session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 实现服务器主动推送实体消息
     * @date 2022/6/11 9:49
     * @version 1.0
     */
    private void sendStatusInfo(Object message) {
        try {
            this.session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得此时的在线人数
     *
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数加1
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线人数减1
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
