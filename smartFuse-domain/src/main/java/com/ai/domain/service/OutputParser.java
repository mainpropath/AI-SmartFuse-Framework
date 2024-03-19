package com.ai.domain.service;

public interface OutputParser<T> {

    T parse(String text);

    String formatInstructions();
}
