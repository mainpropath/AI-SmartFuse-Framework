package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

import java.math.BigDecimal;

public class BigDecimalOutputParser implements OutputParser<BigDecimal> {

    @Override
    public BigDecimal parse(String string) {
        return new BigDecimal(string);
    }

    @Override
    public String formatInstructions() {
        return "floating point number";
    }
}
