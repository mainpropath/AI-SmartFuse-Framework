package com.ai.openai.memory.message;

import com.ai.interfaces.message.ChatMessage;
import com.ai.interfaces.message.ChatMessageFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class OpenaiMessageFactory implements ChatMessageFactory {

    @Override
    public OpenaiSystemMessage createSystemMessage(String content) {
        return OpenaiSystemMessage.builder().content(content).messageType(MessageType.SYSTEM).build();
    }

    @Override
    public OpenaiUserMessage createUserMessage(String content) {
        return OpenaiUserMessage.builder().content(content).messageType(MessageType.USER).build();
    }

    @Override
    public OpenaiAssistantMessage createAssistantMessage(String content) {
        return OpenaiAssistantMessage.builder().content(content).messageType(MessageType.ASSISTANT).build();
    }

    public static ChatMessage createChatMessage(String content, MessageType messageType) {
        switch (messageType) {
            case USER:
                OpenaiUserMessage.builder().content(content).messageType(MessageType.USER).build();
            case SYSTEM:
                return OpenaiSystemMessage.builder().content(content).messageType(MessageType.SYSTEM).build();
            case ASSISTANT:
                OpenaiAssistantMessage.builder().content(content).messageType(MessageType.ASSISTANT).build();
            default:
                throw new IllegalArgumentException("Unsupported object type");
        }
    }

    @Getter
    @AllArgsConstructor
    public enum MessageType {
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");

        private String messageType;
    }

}
