package com.ai.openai.param;

import com.ai.interfaces.param.Parameter;
import com.ai.openai.param.input.OpenaiEmbeddingParam;


public class OpenaiEmbeddingModelParameter implements Parameter<OpenaiEmbeddingParam> {

    private OpenaiEmbeddingParam param;

    public OpenaiEmbeddingModelParameter() {
        this(OpenaiEmbeddingParam.builder().build());
    }

    public OpenaiEmbeddingModelParameter(OpenaiEmbeddingParam param) {
        this.param = param;
    }

    @Override
    public OpenaiEmbeddingParam getParameter() {
        return param;
    }

    @Override
    public void SetParameter(OpenaiEmbeddingParam parameter) {
        this.param = param;
    }

}
