package com.ai.baidu.converter;

import com.ai.baidu.common.Usage;
import com.ai.common.resp.usage.TokenUsage;


public class BeanConverter {

    public static TokenUsage usage2tokenUsage(Usage usage) {
        return TokenUsage.usage(usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
    }

}
