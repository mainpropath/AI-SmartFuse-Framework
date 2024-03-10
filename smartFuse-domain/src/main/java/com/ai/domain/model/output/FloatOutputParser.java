package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class FloatOutputParser implements OutputParser<Float> {

    @Override
    public Float parse(String string) {
        return Float.parseFloat(string);
    }

    @Override
    public String formatInstructions() {
        return "floating point number";
    }
}
