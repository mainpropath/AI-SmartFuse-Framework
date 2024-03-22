package com.ai.domain.document;

import java.util.Objects;

public class TextSegment {

    private final String text;
    private final Metadata metadata;

    public TextSegment(String text, Metadata metadata) {
        this.text = text;
        this.metadata = metadata;
    }

    public static TextSegment from(String text) {
        return new TextSegment(text, new Metadata());
    }

    public static TextSegment from(String text, Metadata metadata) {
        return new TextSegment(text, metadata);
    }

    public static TextSegment textSegment(String text) {
        return from(text);
    }

    public static TextSegment textSegment(String text, Metadata metadata) {
        return from(text, metadata);
    }

    public String text() {
        return text;
    }

    public Metadata metadata() {
        return metadata;
    }

    public String metadata(String key) {
        return metadata.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSegment that = (TextSegment) o;
        return Objects.equals(this.text, that.text)
                && Objects.equals(this.metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, metadata);
    }
}
