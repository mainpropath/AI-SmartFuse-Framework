package com.ai.domain.data.document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: TODO
 **/
public class Metadata {
    private final Map<String, String> metadata;

    public Metadata() {
        this(new HashMap<String, String>());
    }

    public Metadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String get(String key) {
        return (String) this.metadata.get(key);
    }

    public Metadata add(String key, Object value) {
        this.metadata.put(key, value.toString());
        return this;
    }

    public Metadata remove(String key) {
        this.metadata.remove(key);
        return this;
    }

    public Metadata copy() {
        return new Metadata(new HashMap(this.metadata));
    }

    public Map<String, String> asMap() {
        return new HashMap(this.metadata);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Metadata that = (Metadata) o;
            return Objects.equals(this.metadata, that.metadata);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.metadata});
    }

    public String toString() {
        return "Metadata { metadata = " + this.metadata + " }";
    }

    public static Metadata from(String key, Object value) {
        return (new Metadata()).add(key, value);
    }

    public static Metadata from(Map<String, String> metadata) {
        return new Metadata(metadata);
    }

    public static Metadata metadata(String key, Object value) {
        return from(key, value);
    }
}

