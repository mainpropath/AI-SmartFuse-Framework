package com.ai.common.resp.usage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenUsage {

    private Integer inputTokenCount;
    private Integer outputTokenCount;
    private Integer totalTokenCount;

    public static TokenUsage usage(Integer inputTokenCount, Integer outputTokenCount, Integer totalTokenCount) {
        return new TokenUsage(inputTokenCount, outputTokenCount, totalTokenCount);
    }

}
