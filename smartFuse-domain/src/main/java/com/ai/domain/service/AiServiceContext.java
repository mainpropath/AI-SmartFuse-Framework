package com.ai.domain.service;

import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.ModerationModel;
import com.ai.domain.tools.ToolSpecification;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AiServiceContext {

    private Class<?> aiServiceClass;
    private ChatModel chatModel;
    private ModerationModel moderationModel;
    private ChatHistoryRecorder chatHistoryRecorder;
    private List<ToolSpecification> toolSpecifications;

}
