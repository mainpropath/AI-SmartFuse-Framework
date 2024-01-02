package com.ai.domain.tools;

import java.util.Objects;

public class ToolExecutionRequest {

    private final String id;
    private final String name;
    private final String arguments;

    private ToolExecutionRequest(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.arguments = builder.arguments;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String arguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another instanceof ToolExecutionRequest
                && equalTo((ToolExecutionRequest) another);
    }

    private boolean equalTo(ToolExecutionRequest another) {
        return Objects.equals(id, another.id)
                && Objects.equals(name, another.name)
                && Objects.equals(arguments, another.arguments);
    }

    @Override
    public int hashCode() {
        int h = 5381;
        h += (h << 5) + Objects.hashCode(id);
        h += (h << 5) + Objects.hashCode(name);
        h += (h << 5) + Objects.hashCode(arguments);
        return h;
    }

    public static final class Builder {

        private String id;
        private String name;
        private String arguments;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder arguments(String arguments) {
            this.arguments = arguments;
            return this;
        }

        public ToolExecutionRequest build() {
            return new ToolExecutionRequest(this);
        }
    }
}
