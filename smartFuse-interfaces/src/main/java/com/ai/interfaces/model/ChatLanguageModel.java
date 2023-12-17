package com.ai.interfaces.model;

import com.ai.interfaces.message.ChatMessage;

/**
 * @Description: 模型
 **/
public interface ChatLanguageModel {

    String generate(String userMessage);

    String generate(ChatMessage... messages);

//    default Response<AiMessage> generate(ChatMessage... messages) {
//        return this.generate(Arrays.asList(messages));
//    }

}

