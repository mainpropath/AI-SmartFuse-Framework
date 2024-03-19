package com.ai.domain.data.message;

/**
 * @Description: 用户消息
 **/
public class UserMessage extends ChatMessage {

    public UserMessage(String text) {
        super(text);
    }

    @Override
    public MessageType type() {
        return MessageType.USER;
    }

}
