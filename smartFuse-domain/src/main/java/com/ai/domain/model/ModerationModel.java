package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.moderation.Moderation;

import java.util.Arrays;
import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotNull;

public interface ModerationModel {

    /**
     * 对单个文本进行审核
     */
    default AiResponse<Moderation> moderate(String message) {
        ensureNotBlank(message, "message");
        AiResponse<List<Moderation>> moderate = moderate(Arrays.asList(message));
        return AiResponse.R(moderate.getData().get(0), FinishReason.success());
    }

    /**
     * 对多个文本进行审核
     */
    AiResponse<List<Moderation>> moderate(List<String> messages);

    /**
     * 对消息文本进行审核
     */
    default AiResponse<Moderation> moderate(ChatMessage messages) {
        ensureNotNull(messages, "messages");
        return moderate(messages.getText());
    }

}
