package com.ai.common.usage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiResponse<T> {

    private T data;
    private TokenUsage tokenUsage;
    private String finishReason;

}
