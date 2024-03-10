package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

public class LocalTimeOutputParser implements OutputParser<LocalTime> {

    @Override
    public LocalTime parse(String string) {
        return LocalTime.parse(string, ISO_LOCAL_TIME);
    }

    @Override
    public String formatInstructions() {
        return "23:59:59";
    }
}
