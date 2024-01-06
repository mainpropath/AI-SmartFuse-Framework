package com.ai.common.usage;

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

}
