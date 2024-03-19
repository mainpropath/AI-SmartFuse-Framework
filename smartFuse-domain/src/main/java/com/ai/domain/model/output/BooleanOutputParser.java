package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class BooleanOutputParser implements OutputParser<Boolean> {

    @Override
    public Boolean parse(String string) {
        return Boolean.parseBoolean(string);
    }

    @Override
    public String formatInstructions() {
        return "one of [true, false]";
    }
}
