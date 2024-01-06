package com.ai.domain.data.message;

/**
 * @Description: AI消息
 **/
public class AssistantMessage extends ChatMessage {

    public AssistantMessage(String text) {
        super(text);
    }

    @Override
    public MessageType type() {
        return MessageType.ASSISTANT;
    }

}
