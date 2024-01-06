package com.ai.domain.memory.chat;

import com.ai.domain.data.message.ChatMessage;

import java.util.List;

/**
 * 历史聊天信息记录器
 **/
public interface ChatHistoryRecorder {

    String id();

    void add(ChatMessage message);

    List<ChatMessage> getCurrentMessages();

    void clear();

}
