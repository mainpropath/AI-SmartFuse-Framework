package com.ai.openai.memory.message;

import com.ai.interfaces.message.ChatMessage;
import lombok.Builder;

/**
 * @Description: AI消息
 **/
@Builder
public class OpenaiAssistantMessage implements ChatMessage {

    private String content;

    @Builder.Default
    private OpenaiMessageFactory.MessageType messageType = OpenaiMessageFactory.MessageType.ASSISTANT;

    @Override
    public String type() {
        return messageType.getMessageType();
    }

    @Override
    public String content() {
        return this.content;
    }
}
