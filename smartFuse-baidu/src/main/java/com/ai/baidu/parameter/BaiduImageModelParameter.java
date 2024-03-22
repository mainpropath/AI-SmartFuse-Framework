package com.ai.baidu.parameter;

import com.ai.baidu.parameter.input.BaiduImageParameter;
import com.ai.domain.data.parameter.Parameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;

public class BaiduImageModelParameter implements Parameter<BaiduImageParameter> {

    private BaiduImageParameter parameter;

    public BaiduImageModelParameter() {
        this(BaiduImageParameter.builder().build());
    }

    public BaiduImageModelParameter(BaiduImageParameter parameter) {
        this.parameter = ensureNotNull(parameter, "BaiduImageParameter");
    }

    @Override
    public BaiduImageParameter getParameter() {
        return this.parameter;
    }

    @Override
    public void SetParameter(BaiduImageParameter parameter) {
        this.parameter = parameter;
    }
}
