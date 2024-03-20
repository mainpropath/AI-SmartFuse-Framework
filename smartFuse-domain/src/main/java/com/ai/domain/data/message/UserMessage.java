package com.ai.domain.data.message;

/**
 * @Description: 用户消息
 **/
public class UserMessage extends ChatMessage {

    public UserMessage(String text) {
        super(text);
    }

    public static UserMessage message(String message) {
        return new UserMessage(message);
    }

    @Override
    public MessageType type() {
        return MessageType.USER;
    }

}
