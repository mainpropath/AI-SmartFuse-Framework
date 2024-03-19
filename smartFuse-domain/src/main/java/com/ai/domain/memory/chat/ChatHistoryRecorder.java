package com.ai.domain.memory.chat;

import com.ai.domain.data.message.ChatMessage;

import java.util.List;

/**
 * 历史聊天信息记录器
 **/
public interface ChatHistoryRecorder {

    /**
     * 获取当前对话信息的存储ID
     */
    String getId();

    /**
     * 设置当前对话信息的存储ID
     *
     * @param id
     */
    void setId(String id);

    /**
     * 添加信息到当前对话信息列表中
     */
    void add(ChatMessage message);

    /**
     * 得到当前对话信息列表
     */
    List<ChatMessage> getCurrentMessages();

    /**
     * 根据ID在存储器当中获取对话信息列表
     */
    List<ChatMessage> getMessagesById(String id);

    /**
     * 清除当前对话信息列表
     */
    void clear();

    /**
     * 根据ID清除对话信息列表
     */
    void clearById(String id);

}
