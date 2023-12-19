package com.ai.interfaces.model;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

/**
 * @Description: 模型
 **/
public interface ChatLanguageModel<Output> {

    Output generate(String userMessage);

    Output generate(List<ChatMessage> messageList);

}

