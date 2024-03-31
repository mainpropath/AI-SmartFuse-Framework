package com.ai.domain.data.message;


import lombok.Data;

/**
 * 聊天消息
 **/
@Data
public abstract class ChatMessage {

    protected final String text;

    protected final Integer order;

    ChatMessage(String text, Integer order) {
        this.text = text;
        this.order = order;
    }

    public abstract MessageType type();

    public Integer order() {
        return order;
    }

    public String text() {
        return text;
    }

}
