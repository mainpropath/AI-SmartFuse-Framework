package com.ai.interfaces.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Description: 用户消息
 **/
@Data
@SuperBuilder
public class UserMessage extends ChatMessage {
    private final String name;


    @Override
    public ChatMessageType type() {
        return null;
    }
}
