package com.ai.interfaces.memory;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

/**
 * @Description: 历史聊天信息记录器
 **/
public interface ChatHistoryRecorder {

    List<ChatMessage> getMessages(Object var1);

    void updateMessages(Object var1, List<ChatMessage> var2);

    void deleteMessages(Object var1);
}
