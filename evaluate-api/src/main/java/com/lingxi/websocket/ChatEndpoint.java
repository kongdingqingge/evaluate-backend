package com.lingxi.websocket;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingxi.mapper.GScoreUmpireMapper;
import com.lingxi.model.po.GScoreUmpire;
import com.lingxi.model.po.GUmpires;
import com.lingxi.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务端点
 * 主裁判向其余裁判发送下信息
 *
 * @author Cyou
 */
@ServerEndpoint(value = "/chat/{token}")
@Slf4j
@Component
public class ChatEndpoint {

    /**
     * 用来储存在线用户的容器
     */
    public static Map<Integer, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 用来给客户端发送消息
     */
    private Session session;

    private GUmpires gUmpires;

    @Autowired
    private GScoreUmpireMapper gScoreUmpireMapper;

    private static GScoreUmpireMapper staticgScoreUmpireMapper;

    private boolean isHeartbeatRunning = false;

    @PostConstruct
    public void init() {
        staticgScoreUmpireMapper = gScoreUmpireMapper;
    }

    /**
     * 建立时调用
     *
     * @param session
     * @param token
     * @author Cyou
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        if ("999".equals(token)) {
            this.session = session;
            int i = Integer.parseInt(token);
            onlineUsers.put(i, this);
            return;
        }
        //将当前session赋值给属性
        this.session = session;
        //从token获取用户数据
        this.gUmpires = TokenUtils.getUmpires(token);
        Integer umpiresId = gUmpires.getId();
        onlineUsers.put(umpiresId, this);

        // 启动心跳任务
        //startHeartbeatTask();
    }

    /**
     * 接收到客户端发送的数据时调用
     *
     * @param message
     * @param session
     * @author Cyou
     */
    @OnMessage
    public void onMessage(String message, Session session) throws JsonProcessingException {
        if ("{\"bit\":\"bit\"}".equals(message)) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(message, Message.class);
        /*if (msg.isHeartbeat()) {
            return;
        }*/
        ScoreUmpireMessage score = msg.getScore();
        if (score.getUmpireId() != null && score.getProjectId() != null && score.getTeamProjectId() != null && score.getScore() != null) {
            GScoreUmpire gScoreUmpire = BeanUtil.copyProperties(score, GScoreUmpire.class);
            GScoreUmpire scoreUmpire = staticgScoreUmpireMapper.getScoreUmpire(gScoreUmpire);
            if (ObjectUtil.isEmpty(scoreUmpire)) {
                //添加打分记录
                staticgScoreUmpireMapper.addScoreUmpire(gScoreUmpire);
            } else {
                //修改打分记录
                String scoreU = scoreUmpire.getScore();
                String str = scoreU + "," + score.getScore();
                gScoreUmpire.setScore(str);
                staticgScoreUmpireMapper.updateScoreUmpire(gScoreUmpire);
            }
        }
        //获取接收信息的用户
        Integer recipientId = msg.getRecipientId();
        String result = JSONUtil.toJsonStr(message);
        //发送消息
        sendMessageToUser(recipientId, result);
    }

    /**
     * 关闭时从容器里删除用户
     *
     * @param session
     * @param token
     * @author Cyou
     */
    @OnClose
    public void onClose(Session session, @PathParam("token") String token) {
        //从token获取用户数据
        GUmpires tokenUmpires = TokenUtils.getUmpires(token);
        Integer umpiresId = tokenUmpires.getId();
        //从在线用户列表中移除
        onlineUsers.remove(umpiresId);
        // 停止心跳任务
        //stopHeartbeatTask();
    }

    /**
     * 给所有的客户端发送消息
     *
     * @param message 消息
     * @author Cyou
     */
    private void sendMessageToAllUser(String message) {
    }

    /**
     * 发送消息给单个用户
     *
     * @param message 消息
     * @author Cyou
     */
    private void sendMessage(String message) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            } else {
                log.warn("无法发送消息,会话已关闭");
            }
        } catch (IOException e) {
            log.error("发送消息时出现异常: " + e.getMessage(), e);
        }
    }

    private void sendMessageToUser(Integer userId, String message) {
        ChatEndpoint userEndpoint = onlineUsers.get(userId);
        if (userEndpoint != null) {
            log.info("这是：{}", onlineUsers);
            //找到用户的WebSocket会话,向其发送消息
            userEndpoint.sendMessage(message);
        } else {
            log.warn("无法发送消息,用户已不在线");
        }
    }

    public Session getSession() {
        return session;
    }

    /**
     * 心跳检测-开启
     *
     * @author Cyou
     */
    /*private void startHeartbeatTask() {
        isHeartbeatRunning = true;
        Runnable heartbeatTask = () -> {
            while (session.isOpen() && isHeartbeatRunning) {
                try {
                    Thread.sleep(10000);
                    if (session.isOpen()) {
                        Message heartbeatMessage = new Message();
                        heartbeatMessage.setHeartbeat(true);
                        String messageJson = JSONUtil.toJsonStr(heartbeatMessage);
                        sendMessage(messageJson);
                    }
                } catch (InterruptedException e) {
                    log.error("心跳任务出现异常:" + e.getMessage(), e);
                }
            }
        };

        Thread thread = new Thread(heartbeatTask);
        thread.start();
    }*/

    /**
     * 心跳检测-关闭
     *
     * @author Cyou
     */
    /*private void stopHeartbeatTask() {
        isHeartbeatRunning = false;
    }*/

}