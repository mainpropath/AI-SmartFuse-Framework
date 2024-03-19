package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class IntOutputParser implements OutputParser<Integer> {

    @Override
    public Integer parse(String string) {
        return Integer.parseInt(string);
    }

    @Override
    public String formatInstructions() {
        return "integer number";
    }
}
