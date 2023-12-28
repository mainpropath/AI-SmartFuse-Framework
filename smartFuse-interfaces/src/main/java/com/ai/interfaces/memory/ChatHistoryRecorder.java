package com.ai.interfaces.memory;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

/**
 * @Description: 历史聊天信息记录器
 **/
public interface ChatHistoryRecorder {

    Object id();

    void add(ChatMessage message);

    List<ChatMessage> getCurrentMessages();

    void clear();

}
