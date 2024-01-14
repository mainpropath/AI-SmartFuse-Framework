package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiChatParameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;

public class OpenaiChatModelParameter implements Parameter<OpenaiChatParameter> {

    private OpenaiChatParameter parameter;

    public OpenaiChatModelParameter() {
        this(OpenaiChatParameter.builder().build());
    }

    public OpenaiChatModelParameter(OpenaiChatParameter parameter) {
        this.parameter = ensureNotNull(parameter, "OpenaiChatParameter");
    }

    @Override
    public OpenaiChatParameter getParameter() {
        return parameter;
    }

    @Override
    public void SetParameter(OpenaiChatParameter parameter) {
        this.parameter = parameter;
    }

}
