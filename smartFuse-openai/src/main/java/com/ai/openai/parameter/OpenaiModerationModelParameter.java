package com.ai.openai.parameter;


import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiModerationParameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;

public class OpenaiModerationModelParameter implements Parameter<OpenaiModerationParameter> {

    private OpenaiModerationParameter parameter;

    public OpenaiModerationModelParameter() {
        this(OpenaiModerationParameter.builder().build());
    }

    public OpenaiModerationModelParameter(OpenaiModerationParameter parameter) {
        this.parameter = ensureNotNull(parameter, "OpenaiModerationParameter");
    }

    @Override
    public OpenaiModerationParameter getParameter() {
        return this.parameter;
    }

    @Override
    public void SetParameter(OpenaiModerationParameter parameter) {
        this.parameter = parameter;
    }
}
