package com.ai.domain.data.document;

import com.ai.domain.data.segment.TextSegment;

import java.util.Objects;

/**
 * @Description: TODO
 **/
public class Document {
    public static final String DOCUMENT_TYPE = "document_type";
    public static final String FILE_NAME = "file_name";
    public static final String ABSOLUTE_DIRECTORY_PATH = "absolute_directory_path";
    public static final String URL = "url";
    private final String text;
    private final Metadata metadata;

    public Document(String text, Metadata metadata) {
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

    public TextSegment toTextSegment() {
        return TextSegment.from(this.text, this.metadata.copy().add("index", 0));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Document that = (Document) o;
            return Objects.equals(this.text, that.text) && Objects.equals(this.metadata, that.metadata);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.text, this.metadata});
    }

    public static Document from(String text) {
        return new Document(text, new Metadata());
    }

    public static Document from(String text, Metadata metadata) {
        return new Document(text, metadata);
    }

    public static Document document(String text) {
        return from(text);
    }

    public static Document document(String text, Metadata metadata) {
        return from(text, metadata);
    }
}

