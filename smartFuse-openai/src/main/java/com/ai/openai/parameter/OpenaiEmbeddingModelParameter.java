package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiEmbeddingParameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;


public class OpenaiEmbeddingModelParameter implements Parameter<OpenaiEmbeddingParameter> {

    private OpenaiEmbeddingParameter parameter;

    public OpenaiEmbeddingModelParameter() {
        this(OpenaiEmbeddingParameter.builder().build());
    }

    public OpenaiEmbeddingModelParameter(OpenaiEmbeddingParameter parameter) {
        this.parameter = ensureNotNull(parameter, "OpenaiEmbeddingParameter");
    }

    @Override
    public OpenaiEmbeddingParameter getParameter() {
        return parameter;
    }

    @Override
    public void SetParameter(OpenaiEmbeddingParameter parameter) {
        this.parameter = parameter;
    }

}
