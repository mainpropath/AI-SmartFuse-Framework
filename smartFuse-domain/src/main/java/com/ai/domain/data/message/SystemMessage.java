package com.ai.domain.data.message;

/**
 * @Description: 系统消息
 **/
public class SystemMessage extends ChatMessage {

    public SystemMessage(String text, Integer order) {
        super(text, order);
    }

    public static SystemMessage message(String message) {
        return new SystemMessage(message, -1);
    }

    public static SystemMessage message(String message, Integer order) {
        return new SystemMessage(message, order);
    }

    @Override
    public MessageType type() {
        return MessageType.SYSTEM;
    }

}
