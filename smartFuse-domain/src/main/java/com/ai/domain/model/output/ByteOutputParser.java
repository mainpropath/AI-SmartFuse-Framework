package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

public class ByteOutputParser implements OutputParser<Byte> {

    @Override
    public Byte parse(String string) {
        return Byte.parseByte(string);
    }

    @Override
    public String formatInstructions() {
        return "integer number in range [-128, 127]";
    }
}
