package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class ShortOutputParser implements OutputParser<Short> {

    @Override
    public Short parse(String string) {
        return Short.parseShort(string);
    }

    @Override
    public String formatInstructions() {
        return "integer number in range [-32768, 32767]";
    }
}
