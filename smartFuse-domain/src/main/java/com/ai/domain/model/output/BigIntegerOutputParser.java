package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

import java.math.BigInteger;

public class BigIntegerOutputParser implements OutputParser<BigInteger> {

    @Override
    public BigInteger parse(String string) {
        return new BigInteger(string);
    }

    @Override
    public String formatInstructions() {
        return "integer number";
    }
}
