package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class LongOutputParser implements OutputParser<Long> {

    @Override
    public Long parse(String string) {
        return Long.parseLong(string);
    }

    @Override
    public String formatInstructions() {
        return "integer number";
    }
}
