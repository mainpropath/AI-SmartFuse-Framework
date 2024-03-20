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

    public static <T> AiResponse<T> R(T data, TokenUsage tokenUsage, FinishReason finishReason) {
        return new AiResponse<>(data, tokenUsage, finishReason);
    }

    public static <T> AiResponse<T> R(T data) {
        return R(data, null, null);
    }

    public static <T> AiResponse<T> R(T data, FinishReason finishReason) {
        return R(data, null, finishReason);
    }

    public static <T> AiResponse<T> R(T data, TokenUsage tokenUsage) {
        return R(data, tokenUsage, null);
    }

}
