package com.ai.interfaces.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Description: AI消息
 **/
@Data
@SuperBuilder
public class AiMessage extends ChatMessage {
    @Override
    public ChatMessageType type() {
        return null;
    }
}
