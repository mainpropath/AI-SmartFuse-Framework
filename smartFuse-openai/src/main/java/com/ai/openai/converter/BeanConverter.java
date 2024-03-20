package com.ai.openai.converter;

import com.ai.common.resp.usage.TokenUsage;
import com.ai.openai.common.Usage;

public class BeanConverter {

    public static TokenUsage usage2tokenUsage(Usage usage) {
        return TokenUsage.usage(usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
    }

}
