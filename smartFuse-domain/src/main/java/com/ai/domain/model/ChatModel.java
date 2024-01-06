package com.ai.domain.model;


import com.ai.common.usage.AiResponse;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.message.UserMessage;

import java.util.List;

import static java.util.Arrays.asList;

public interface ChatModel {

    default String generate(String userMessage) {
        return generate(new UserMessage(userMessage)).getData().text();
    }

    default AiResponse<AssistantMessage> generate(ChatMessage... messages) {
        return generate(asList(messages));
    }

    AiResponse<AssistantMessage> generate(List<ChatMessage> messages);


}
