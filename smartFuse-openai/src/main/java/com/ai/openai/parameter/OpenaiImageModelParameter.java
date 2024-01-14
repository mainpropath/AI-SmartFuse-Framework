package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiImageParameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;

public class OpenaiImageModelParameter implements Parameter<OpenaiImageParameter> {

    private OpenaiImageParameter parameter;

    public OpenaiImageModelParameter() {
        this(OpenaiImageParameter.builder().build());
    }

    public OpenaiImageModelParameter(OpenaiImageParameter parameter) {
        this.parameter = ensureNotNull(parameter, "OpenaiImageParameter");
    }

    @Override
    public OpenaiImageParameter getParameter() {
        return parameter;
    }

    @Override
    public void SetParameter(OpenaiImageParameter parameter) {
        this.parameter = parameter;
    }

}
