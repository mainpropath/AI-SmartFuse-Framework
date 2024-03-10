package com.ai.domain.service;

import com.ai.common.resp.AiResponse;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.model.output.*;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.util.Arrays.asList;

public class ServiceOutputParser {

    private static final Map<Class<?>, OutputParser<?>> OUTPUT_PARSERS = new HashMap<>();

    static {
        OUTPUT_PARSERS.put(boolean.class, new BooleanOutputParser());
        OUTPUT_PARSERS.put(Boolean.class, new BooleanOutputParser());

        OUTPUT_PARSERS.put(byte.class, new ByteOutputParser());
        OUTPUT_PARSERS.put(Byte.class, new ByteOutputParser());

        OUTPUT_PARSERS.put(short.class, new ShortOutputParser());
        OUTPUT_PARSERS.put(Short.class, new ShortOutputParser());

        OUTPUT_PARSERS.put(int.class, new IntOutputParser());
        OUTPUT_PARSERS.put(Integer.class, new IntOutputParser());

        OUTPUT_PARSERS.put(long.class, new LongOutputParser());
        OUTPUT_PARSERS.put(Long.class, new LongOutputParser());

        OUTPUT_PARSERS.put(BigInteger.class, new BigIntegerOutputParser());

        OUTPUT_PARSERS.put(float.class, new FloatOutputParser());
        OUTPUT_PARSERS.put(Float.class, new FloatOutputParser());

        OUTPUT_PARSERS.put(double.class, new DoubleOutputParser());
        OUTPUT_PARSERS.put(Double.class, new DoubleOutputParser());

        OUTPUT_PARSERS.put(BigDecimal.class, new BigDecimalOutputParser());

        OUTPUT_PARSERS.put(Date.class, new DateOutputParser());
        OUTPUT_PARSERS.put(LocalDate.class, new LocalDateOutputParser());
        OUTPUT_PARSERS.put(LocalTime.class, new LocalTimeOutputParser());
        OUTPUT_PARSERS.put(LocalDateTime.class, new LocalDateTimeOutputParser());
    }

    public static Object parse(AiResponse<AssistantMessage> response, Class<?> returnType) {

        if (returnType == AiResponse.class) {
            return response;
        }

        AssistantMessage aiMessage = response.getData();
        if (returnType == AssistantMessage.class) {
            return aiMessage;
        }

        String text = aiMessage.text();
        if (returnType == String.class) {
            return text;
        }

        OutputParser<?> outputParser = OUTPUT_PARSERS.get(returnType);
        if (outputParser != null) {
            return outputParser.parse(text);
        }

        if (returnType == List.class) {
            return asList(text.split("\n"));
        }

        if (returnType == Set.class) {
            return new HashSet<>(asList(text.split("\n")));
        }

        return new Gson().fromJson(text, returnType);
    }
}
