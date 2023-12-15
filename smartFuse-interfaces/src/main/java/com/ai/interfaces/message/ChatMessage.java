package com.ai.interfaces.message;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @Description: 聊天消息
 **/
@Data
@SuperBuilder
public abstract class ChatMessage {

    protected final String text;

    public abstract ChatMessageType type();

    public enum ChatMessageType {
        SYSTEM,
        USER,
        AI,
        TOOL_EXECUTION_RESULT;

        private ChatMessageType() {
        }

        public static Class<? extends ChatMessage> classOf(ChatMessageType type) {
            switch (type) {
                case SYSTEM:
                    return SystemMessage.class;
                case USER:
                    return UserMessage.class;
                case AI:
                    return AiMessage.class;
                default:
                    throw new RuntimeException("Unknown ChatMessageType: " + type.getClass());
            }
        }
    }

}
