package com.ai.openai.memory.message;

import com.ai.interfaces.message.ChatMessage;
import lombok.Builder;
import lombok.Data;

/**
 * @Description: 用户消息
 **/
@Builder
@Data
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
