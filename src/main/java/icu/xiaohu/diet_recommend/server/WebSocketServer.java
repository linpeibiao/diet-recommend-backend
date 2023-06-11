package icu.xiaohu.diet_recommend.server;

import icu.xiaohu.diet_recommend.model.entity.Message;
import icu.xiaohu.diet_recommend.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaohu
 * @date 2023/06/02/ 14:48
 * @description
 */
@Slf4j
public abstract class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    protected volatile AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     */
    protected static ConcurrentHashMap<Long, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 管理员连接单独维护
     */
    protected static ConcurrentHashMap<Long, WebSocketServer> adminWebSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    protected Session session;
    /**
     * 接收 userId 用于区别
     */
    protected Long userId;

    @Resource
    protected MessageService messageService;

    /**
     * 发送信息
     *
     * @param message
     */
    public abstract void sendInfo(Message message);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public abstract void onOpen(Session session, Long userId);

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public abstract void onClose();

    /**
     * @param message 客户端发送过来的消息
     * @param session session
     **/
    @OnMessage
    public abstract void onMessage(String message, Session session);

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
        try {
            session.getBasicRemote().sendText("服务端错误");
        } catch (IOException e) {
            e.printStackTrace();
            error.printStackTrace();
        }
    }

    /**
     * 实现服务器主动推送
     */
    protected void sendMessage(String message) {
        try {
            log.info("websocket主动推送数据：{}", message);
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
    public final int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 在线人数加1
     */
    public final void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    /**
     * 在线人数减1
     */
    public final void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}
