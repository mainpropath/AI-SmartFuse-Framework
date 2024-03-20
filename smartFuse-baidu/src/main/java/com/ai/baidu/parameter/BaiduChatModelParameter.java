package com.ai.baidu.parameter;

import com.ai.baidu.parameter.input.BaiduChatParameter;
import com.ai.domain.data.parameter.Parameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;


public class BaiduChatModelParameter implements Parameter<BaiduChatParameter> {

    private BaiduChatParameter parameter;

    public BaiduChatModelParameter() {
        this(BaiduChatParameter.builder().build());
    }

    public BaiduChatModelParameter(BaiduChatParameter parameter) {
        this.parameter = ensureNotNull(parameter, "BaiduChatParameter");
    }

    @Override
    public BaiduChatParameter getParameter() {
        return this.parameter;
    }

    @Override
    public void SetParameter(BaiduChatParameter parameter) {
        this.parameter = parameter;
    }
}
