package com.ai.openai.memory.chat.message;

import com.ai.interfaces.message.ChatMessage;
import lombok.Builder;

/**
 * @Description: 用户消息
 **/
@Builder
public class OpenaiUserMessage implements ChatMessage {

    private String content;

    @Builder.Default
    private OpenaiMessageFactory.MessageType messageType = OpenaiMessageFactory.MessageType.USER;

    @Override
    public String type() {
        return messageType.getMessageType();
    }

    @Override
    public String content() {
        return this.content;
    }

}
