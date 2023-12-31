package com.ai.openai.param;

import com.ai.interfaces.param.Parameter;
import com.ai.openai.param.input.OpenaiImageParam;


public class OpenaiImageModelParameter implements Parameter<OpenaiImageParam> {

    private OpenaiImageParam param;

    public OpenaiImageModelParameter() {
        this(OpenaiImageParam.builder().build());
    }

    public OpenaiImageModelParameter(OpenaiImageParam param) {
        this.param = param;
    }

    @Override
    public OpenaiImageParam getParameter() {
        return param;
    }

    @Override
    public void SetParameter(OpenaiImageParam parameter) {
        this.param = parameter;
    }

}
