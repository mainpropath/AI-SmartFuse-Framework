package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiChatParameter;

public class OpenaiChatModelParameter implements Parameter<OpenaiChatParameter> {

    private OpenaiChatParameter param;

    public OpenaiChatModelParameter() {
        this(OpenaiChatParameter.builder().build());
    }

    public OpenaiChatModelParameter(OpenaiChatParameter param) {
        this.param = param;
    }

    @Override
    public OpenaiChatParameter getParameter() {
        return param;
    }

    @Override
    public void SetParameter(OpenaiChatParameter parameter) {
        this.param = parameter;
    }

}
