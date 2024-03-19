package com.ai.domain.memory.chat;

import com.ai.domain.data.message.ChatMessage;

import java.util.List;

/**
 * 历史聊天信息存储器
 */
public interface ChatMemoryStore {

    /**
     * 根据ID获取对话信息列表
     */
    List<ChatMessage> getMessages(String memoryId);

    /**
     * 根据ID修改对话信息列表
     */
    void updateMessages(String memoryId, List<ChatMessage> msgList);

    /**
     * 根据ID删除对话信息列表
     */
    void deleteMessages(String memoryId);

}
