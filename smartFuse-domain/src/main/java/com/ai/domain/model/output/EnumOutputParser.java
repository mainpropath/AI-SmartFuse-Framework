package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;
import com.google.gson.Gson;

import java.util.Arrays;

public class EnumOutputParser implements OutputParser<Enum> {

    private final Class<? extends Enum> enumClass;

    public EnumOutputParser(Class<? extends Enum> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Enum parse(String string) {
        return new Gson().fromJson(string, enumClass);
    }

    @Override
    public String formatInstructions() {
        return "one of " + Arrays.toString(enumClass.getEnumConstants());
    }
}
