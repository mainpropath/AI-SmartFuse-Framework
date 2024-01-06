package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiImageParameter;

public class OpenaiImageModelParameter implements Parameter<OpenaiImageParameter> {

    private OpenaiImageParameter param;

    public OpenaiImageModelParameter() {
        this(OpenaiImageParameter.builder().build());
    }

    public OpenaiImageModelParameter(OpenaiImageParameter param) {
        this.param = param;
    }

    @Override
    public OpenaiImageParameter getParameter() {
        return param;
    }

    @Override
    public void SetParameter(OpenaiImageParameter parameter) {
        this.param = parameter;
    }

}
