package com.ai.domain.data.message;

/**
 * @Description: AI消息
 **/
public class AssistantMessage extends ChatMessage {

    public AssistantMessage(String text) {
        super(text);
    }

    public static AssistantMessage message(String message) {
        return new AssistantMessage(message);
    }

    @Override
    public MessageType type() {
        return MessageType.ASSISTANT;
    }

}
