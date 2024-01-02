package com.ai.openai.memory.chat.message;

import com.ai.interfaces.message.ChatMessage;
import lombok.Builder;

/**
 * @Description: 系统消息
 **/
@Builder
public class OpenaiSystemMessage implements ChatMessage {

    private String content;

    @Builder.Default
    private OpenaiMessageFactory.MessageType messageType = OpenaiMessageFactory.MessageType.SYSTEM;

    @Override
    public String type() {
        return messageType.getMessageType();
    }

    @Override
    public String content() {
        return this.content;
    }
}
