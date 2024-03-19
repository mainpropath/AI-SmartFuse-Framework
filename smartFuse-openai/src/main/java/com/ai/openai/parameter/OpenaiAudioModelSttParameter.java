package com.ai.openai.parameter;

import com.ai.domain.data.parameter.Parameter;
import com.ai.openai.parameter.input.OpenaiAudioSttParameter;

import static com.ai.common.util.ValidationUtils.ensureNotNull;


public class OpenaiAudioModelSttParameter implements Parameter<OpenaiAudioSttParameter> {

    private OpenaiAudioSttParameter parameter;

    public OpenaiAudioModelSttParameter() {
        this(OpenaiAudioSttParameter.builder().build());
    }

    public OpenaiAudioModelSttParameter(OpenaiAudioSttParameter parameter) {
        this.parameter = ensureNotNull(parameter, "OpenaiAudioSttParameter");
    }

    @Override
    public OpenaiAudioSttParameter getParameter() {
        return this.parameter;
    }

    @Override
    public void SetParameter(OpenaiAudioSttParameter parameter) {
        this.parameter = parameter;
    }
}
