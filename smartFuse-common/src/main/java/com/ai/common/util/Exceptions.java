package com.ai.common.util;


public class Exceptions {

    public static IllegalArgumentException illegalArgument(String format, Object... args) {
        return new IllegalArgumentException(String.format(format, args));
    }

    public static RuntimeException runtime(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }
}
