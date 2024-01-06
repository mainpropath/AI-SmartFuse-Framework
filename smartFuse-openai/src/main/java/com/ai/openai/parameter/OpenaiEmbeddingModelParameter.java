package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiEmbeddingParameter;


public class OpenaiEmbeddingModelParameter implements Parameter<OpenaiEmbeddingParameter> {

    private OpenaiEmbeddingParameter param;

    public OpenaiEmbeddingModelParameter() {
        this(OpenaiEmbeddingParameter.builder().build());
    }

    public OpenaiEmbeddingModelParameter(OpenaiEmbeddingParameter param) {
        this.param = param;
    }

    @Override
    public OpenaiEmbeddingParameter getParameter() {
        return param;
    }

    @Override
    public void SetParameter(OpenaiEmbeddingParameter parameter) {
        this.param = param;
    }

}
