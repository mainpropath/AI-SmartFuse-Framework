package com.ai.interfaces.memory.chat;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

/**
 * @Description: 历史聊天信息记录器
 **/
public interface ChatHistoryRecorder {

    String id();

    void add(ChatMessage message);

    List<ChatMessage> getCurrentMessages();

    void clear();

}
