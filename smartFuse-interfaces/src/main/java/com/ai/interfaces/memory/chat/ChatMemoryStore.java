package com.ai.interfaces.memory.chat;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

/**
 * 历史聊天信息存储器
 */
public interface ChatMemoryStore {

    List<ChatMessage> getMessages(Object msg);

    void updateMessages(Object msg, List<ChatMessage> msgList);

    void deleteMessages(Object msg);

}
