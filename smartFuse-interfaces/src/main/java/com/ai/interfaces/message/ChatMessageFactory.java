package com.ai.interfaces.message;

/**
 * 聊天信息工厂
 */
public interface ChatMessageFactory {

    ChatMessage createSystemMessage(String content);

    ChatMessage createUserMessage(String content);

    ChatMessage createAssistantMessage(String content);

}
