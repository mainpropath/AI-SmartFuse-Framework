package com.ai.domain.service;

import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.ModerationModel;
import com.ai.domain.spi.AiServicesFactory;
import com.ai.domain.spi.ServiceHelper;

import java.util.Collection;

public abstract class AiServices<T> {

    protected AiServiceContext context;

    protected AiServices(AiServiceContext context) {
        this.context = context;
    }

    public static <T> AiServices<T> builder(Class<T> aiService) {
        AiServiceContext context = AiServiceContext.builder().aiServiceClass(aiService).build();
        Collection<AiServicesFactory> aiServicesFactories = ServiceHelper.loadFactories(AiServicesFactory.class);
        for (AiServicesFactory factory : aiServicesFactories) {
            return factory.create(context);
        }
        // fallback to default
        return new DefaultAiServices<>(context);
    }

    public AiServices<T> chat(ChatModel chatModel) {
        context.setChatModel(chatModel);
        return this;
    }

    public AiServices<T> memory(ChatHistoryRecorder chatHistoryRecorder) {
        context.setChatHistoryRecorder(chatHistoryRecorder);
        return this;
    }

    public AiServices<T> moderate(ModerationModel moderationModel) {
        context.setModerationModel(moderationModel);
        return this;
    }

    public abstract T build();

    public void performValidation() {
        if (this.context.getChatModel() == null) {
            throw new IllegalArgumentException("No chat model set");
        }
    }

}
