package com.ai.domain.data.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private String messageType;
}

