package com.ai.openai.param;

import com.ai.interfaces.param.Parameter;
import com.ai.openai.data.input.OpenaiChatParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenaiChatModelParameter implements Parameter<OpenaiChatParam> {

    private OpenaiChatParam param;

    @Override
    public OpenaiChatParam getParameter() {
        if (param != null) return param;
        return OpenaiChatParam.builder().build();
    }
}
