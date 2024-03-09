package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiAudioTtsParameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;


public class OpenaiAudioModelTtsParameter implements Parameter<OpenaiAudioTtsParameter> {

    private OpenaiAudioTtsParameter parameter;

    public OpenaiAudioModelTtsParameter() {
        this(OpenaiAudioTtsParameter.builder().build());
    }

    public OpenaiAudioModelTtsParameter(OpenaiAudioTtsParameter parameter) {
        this.parameter = ensureNotNull(parameter, "OpenaiAudioTtsParameter");
    }

    @Override
    public OpenaiAudioTtsParameter getParameter() {
        return this.parameter;
    }


    @Override
    public void SetParameter(OpenaiAudioTtsParameter parameter) {
        this.parameter = parameter;
    }
}
