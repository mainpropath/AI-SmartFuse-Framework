package com.ai.interfaces.message;

public interface ChatMessageFactory {

    ChatMessage createSystemMessage(String content);

    ChatMessage createUserMessage(String content);

    ChatMessage createAssistantMessage(String content);

}
