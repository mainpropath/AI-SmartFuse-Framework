package com.ai.common.resp.finish;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishReason {

    private String description;

    public static FinishReason Finish(String description) {
        return new FinishReason(description);
    }

    public static FinishReason success() {
        return new FinishReason("success");
    }

    public static FinishReason error() {
        return new FinishReason("error");
    }

    public static FinishReason timeout() {
        return new FinishReason("timeout");
    }

}
