package icu.xiaohu.diet_recommend.server;

import cn.hutool.json.JSONUtil;
import icu.xiaohu.diet_recommend.model.entity.Message;
import icu.xiaohu.diet_recommend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

/**
 * @author xiaohu
 * @date 2023/06/11/ 16:02
 * @description 管理员会话
 */
@Slf4j
@Component("adminWebServer")
@ServerEndpoint("/server/admin/{userId}")
public class AdminWebServer extends WebSocketServer {
    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        AdminWebServer.messageService = messageService;
    }

    @Override
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
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
        log.info("管理员连接:" + userId + ",当前在线人数为:" + getOnlineCount());
        goCheck();
    }

    private void goCheck() {
        // 建立连接之后查询是否有待办消息
        List<Message> notCheckMessage = messageService.getNotCheckMessage();
        String jsonStr = JSONUtil.toJsonStr(notCheckMessage);
        this.sendMessage(jsonStr);
        log.info("未审核消息:{}", jsonStr);
    }

    @Override
    public void sendInfo(Message message) {
        Long toUserId = message.getConsumer();
        log.info("发送消息到:" + toUserId + "，报文:" + message);
        if (webSocketMap.containsKey(toUserId)) {
            webSocketMap.get(toUserId).sendMessage(message.getContent());
        } else {
            log.error("用户" + toUserId + ",不在线！");
            messageService.save(message);
        }
    }

    @Override
    @OnClose
    public void onClose() {
        if (adminWebSocketMap.containsKey(userId)) {
            // 从 map 中删除
            adminWebSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("管理员退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    @Override
    @OnMessage
    public void onMessage(String data, Session session) {

    }
}
