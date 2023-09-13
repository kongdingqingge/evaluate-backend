package com.lingxi.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    /**
     * 发送者id
     */
    private Integer senderId;

    /**
     * 接收者id
     */
    private Integer recipientId;

    /**
     * 内容
     */
    private String msg;

    /**
     * 头像
     */
    private String avatar;

    private ScoreUmpireMessage score;

    private NameList nameList;

    /**
     * 表示是否为心跳消息
     */
    //private boolean isHeartbeat;
}