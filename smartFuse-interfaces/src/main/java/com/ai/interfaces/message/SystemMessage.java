package com.ai.interfaces.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Description: 系统消息
 **/
@Data
@SuperBuilder
public class SystemMessage extends ChatMessage {
    @Override
    public ChatMessageType type() {
        return null;
    }
}
