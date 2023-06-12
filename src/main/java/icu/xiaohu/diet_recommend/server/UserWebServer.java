package icu.xiaohu.diet_recommend.server;

import icu.xiaohu.diet_recommend.constant.MessageType;
import icu.xiaohu.diet_recommend.model.entity.Message;
import icu.xiaohu.diet_recommend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;

/**
 * @author xiaohu
 * @date 2023/06/11/ 16:48
 * @description
 */
@Slf4j
@Component("userWebServer")
@ServerEndpoint("/server/user/{userId}")
public class UserWebServer extends WebSocketServer {
    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        UserWebServer.messageService = messageService;
    }
    @Override
    public void sendInfo(Message message) {
        String type = message.getType();
        if (MessageType.ADD_CHECK.getType().equals(type)) {
            // 判断是否有管理员在线
            if (!adminWebSocketMap.isEmpty()) {
                for (Map.Entry<Long, WebSocketServer> admin : adminWebSocketMap.entrySet()) {
                    admin.getValue().sendMessage(message.getContent());
                }
            } else {
                // 落库
                messageService.save(message);
            }
        } else {
            // 不是审核信息
            log.info("非审核消息实体：" + message);
        }

    }

    @Override
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
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
        log.info("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        // TODO 给上线的用户推送审核结果消息
    }


    @Override
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(userId)) {
            // 从 map 中删除
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    @Override
    @OnMessage
    public void onMessage(String data, Session session) {
//        log.info("用户消息:" + userId + ",报文:" + data);
//        if (data != null) {
//            try {
//                // 解析发送的报文
//                // 追加发送人(防止串改)
//                Long toUserId = data.getConsumer();
//                String message = data.getContent();
//                // 传送给对应toUserId的websocket
//                if (webSocketMap.containsKey(toUserId)) {
//                    // 业务
//                    webSocketMap.get(toUserId).sendMessage(message);
//                } else {
//                    // userId不存在
//                    // TODO 将消息保存在数据库，等用户上线再推送
//                    log.error("请求的userId:" + toUserId + "不在该服务器上");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

}
