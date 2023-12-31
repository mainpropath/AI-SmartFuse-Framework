package com.ai.domain.data.segment;

import com.ai.domain.data.document.Metadata;

import java.util.Objects;

/**
 * @Description: TODO
 **/
public class TextSegment {
    private final String text;
    private final Metadata metadata;

    public TextSegment(String text, Metadata metadata) {
        this.text = text;
        this.metadata = metadata;
    }

    public String text() {
        return this.text;
    }

    public Metadata metadata() {
        return this.metadata;
    }

    public String metadata(String key) {
        return this.metadata.get(key);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            TextSegment that = (TextSegment) o;
            return Objects.equals(this.text, that.text) && Objects.equals(this.metadata, that.metadata);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.text, this.metadata});
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
}

