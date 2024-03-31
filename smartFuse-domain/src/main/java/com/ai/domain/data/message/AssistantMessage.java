package com.ai.domain.data.message;

/**
 * @Description: AI消息
 **/
public class AssistantMessage extends ChatMessage {

    public AssistantMessage(String text, Integer order) {
        super(text, order);
    }

    public static AssistantMessage message(String message) {
        return new AssistantMessage(message, -1);
    }

    public static AssistantMessage message(String message, Integer order) {
        return new AssistantMessage(message, order);
    }

    @Override
    public MessageType type() {
        return MessageType.ASSISTANT;
    }

}
