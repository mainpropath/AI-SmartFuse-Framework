package com.ai.common.resp;


import com.ai.common.resp.finish.FinishReason;
import com.ai.common.resp.usage.TokenUsage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiResponse<T> {

    private T data;
    private TokenUsage tokenUsage;
    private FinishReason finishReason;

}
