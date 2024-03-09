package com.ai.domain.service;

import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.memory.chat.ChatMemoryStore;
import com.ai.domain.model.ChatModel;
import com.ai.domain.tools.ToolSpecification;

import java.util.List;

public class AiServiceContext {

    public final Class<?> aiServiceClass;
    public ChatModel chatModel;
    public ChatMemoryStore chatMemoryStore;
    public ChatHistoryRecorder chatHistoryRecorder;
    public List<ToolSpecification> toolSpecifications;

    public AiServiceContext(Class<?> aiServiceClass) {
        this.aiServiceClass = aiServiceClass;
    }

}
