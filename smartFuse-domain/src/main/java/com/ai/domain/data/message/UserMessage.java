package com.ai.domain.data.message;

/**
 * @Description: 用户消息
 **/
public class UserMessage extends ChatMessage {

    public UserMessage(String text, Integer order) {
        super(text, order);
    }

    public static UserMessage message(String message) {
        return new UserMessage(message, -1);
    }

    public static UserMessage message(String message, Integer order) {
        return new UserMessage(message, order);
    }

    @Override
    public MessageType type() {
        return MessageType.USER;
    }

}
