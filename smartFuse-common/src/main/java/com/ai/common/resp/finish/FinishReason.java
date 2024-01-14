package com.ai.common.resp.finish;


public enum FinishReason {

    TIMEOUT("time out"),
    ERROR("error"),
    SUCCESS("success");

    private final String description;

    FinishReason(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
