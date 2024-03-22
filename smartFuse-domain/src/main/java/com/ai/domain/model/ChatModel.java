package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.message.UserMessage;

import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static java.util.Arrays.asList;

public interface ChatModel {

    /**
     * 单次问答
     */
    default String generate(String userMessage) {
        ensureNotBlank(userMessage, "userMessage");
        return generate(UserMessage.message(userMessage)).getData().text();
    }

    /**
     * 多轮问答
     */
    default AiResponse<AssistantMessage> generate(ChatMessage... messages) {
        return generate(asList(messages));
    }

    /**
     * 多轮问答
     */
    AiResponse<AssistantMessage> generate(List<ChatMessage> messages);

}
