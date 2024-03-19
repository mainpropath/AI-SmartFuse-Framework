package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class DoubleOutputParser implements OutputParser<Double> {

    @Override
    public Double parse(String string) {
        return Double.parseDouble(string);
    }

    @Override
    public String formatInstructions() {
        return "floating point number";
    }
}
