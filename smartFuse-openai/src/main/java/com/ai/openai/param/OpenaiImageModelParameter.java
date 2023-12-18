package com.ai.openai.param;

import com.ai.interfaces.param.Parameter;
import com.ai.openai.data.input.OpenaiImageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenaiImageModelParameter implements Parameter<OpenaiImageParam> {

    private OpenaiImageParam param;

    @Override
    public OpenaiImageParam getParameter() {
        if (param != null) return param;
        return OpenaiImageParam.builder().build();
    }

}
