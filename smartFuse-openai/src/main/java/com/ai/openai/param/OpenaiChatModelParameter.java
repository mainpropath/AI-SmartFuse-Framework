package com.ai.openai.param;

import com.ai.interfaces.param.Parameter;
import com.ai.openai.param.input.OpenaiChatParam;

public class OpenaiChatModelParameter implements Parameter<OpenaiChatParam> {

    private OpenaiChatParam param;

    public OpenaiChatModelParameter() {
        this(OpenaiChatParam.builder().build());
    }

    public OpenaiChatModelParameter(OpenaiChatParam param) {
        this.param = param;
    }

    @Override
    public OpenaiChatParam getParameter() {
        return param;
    }

    @Override
    public void SetParameter(OpenaiChatParam parameter) {
        this.param = parameter;
    }

}
