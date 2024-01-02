package com.ai.domain.document.splitter.impl;

import java.util.function.Function;

class SegmentBuilder {

    private final int maxSegmentSize;
    private final Function<String, Integer> sizeFunction;
    private final String joinSeparator;
    private StringBuilder segmentBuilder;

    SegmentBuilder(int maxSegmentSize, Function<String, Integer> sizeFunction, String joinSeparator) {
        this.segmentBuilder = new StringBuilder();
        this.maxSegmentSize = maxSegmentSize;
        this.sizeFunction = sizeFunction;
        this.joinSeparator = joinSeparator;
    }

    boolean hasSpaceFor(String text) {
        if (isNotEmpty()) {
            return sizeOf(segmentBuilder.toString()) + sizeOf(joinSeparator) + sizeOf(text) <= maxSegmentSize;
        } else {
            return sizeOf(text) <= maxSegmentSize;
        }
    }

    private int sizeOf(String text) {
        return sizeFunction.apply(text);
    }

    void append(String text) {
        if (isNotEmpty()) {
            segmentBuilder.append(joinSeparator);
        }
        segmentBuilder.append(text);
    }

    void prepend(String text) {
        if (isNotEmpty()) {
            segmentBuilder.insert(0, text + joinSeparator);
        } else {
            segmentBuilder.insert(0, text);
        }
    }

    boolean isNotEmpty() {
        return segmentBuilder.length() > 0;
    }

    String build() {
        return segmentBuilder.toString().trim();
    }

    void reset() {
        segmentBuilder = new StringBuilder();
    }
}
