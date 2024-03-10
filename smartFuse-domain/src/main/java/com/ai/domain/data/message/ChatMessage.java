package com.ai.domain.data.message;


import lombok.Data;

/**
 * 聊天消息
 **/
@Data
public abstract class ChatMessage {

    protected final String text;

    ChatMessage(String text) {
        this.text = text;
    }

    public abstract MessageType type();

    public String text() {
        return text;
    }

}
