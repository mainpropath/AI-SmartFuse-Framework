package com.ai.domain.model.output;

import com.ai.domain.service.OutputParser;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class LocalDateOutputParser implements OutputParser<LocalDate> {

    @Override
    public LocalDate parse(String string) {
        return LocalDate.parse(string, ISO_LOCAL_DATE);
    }

    @Override
    public String formatInstructions() {
        return "2023-12-31";
    }
}
