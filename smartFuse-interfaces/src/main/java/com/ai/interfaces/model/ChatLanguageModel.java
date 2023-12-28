package com.ai.interfaces.model;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

/**
 * @Description: 模型执行接口，当做底层模型和链路的中间层，主要用于屏蔽底层模型调用方式的不同，上层只需关注接口，无需关注底层实现。
 **/
public interface ChatLanguageModel<Output> {

    Output generate(String userMessage);

    Output generate(List<ChatMessage> messageList);

}

