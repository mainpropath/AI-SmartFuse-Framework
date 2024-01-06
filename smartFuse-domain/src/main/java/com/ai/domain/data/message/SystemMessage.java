package com.ai.domain.data.message;

/**
 * @Description: 系统消息
 **/
public class SystemMessage extends ChatMessage {

    public SystemMessage(String text) {
        super(text);
    }

    @Override
    public MessageType type() {
        return MessageType.SYSTEM;
    }

}
