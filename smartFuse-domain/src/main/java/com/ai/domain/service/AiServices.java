package com.ai.domain.service;

import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.memory.chat.ChatMemoryStore;
import com.ai.domain.model.ChatModel;
import com.ai.domain.spi.AiServicesFactory;
import com.ai.domain.spi.ServiceHelper;

import java.util.Arrays;
import java.util.Collection;

public abstract class AiServices<T> {

    protected static final String DEFAULT = "default";

    protected final AiServiceContext context;

    protected AiServices(AiServiceContext context) {
        this.context = context;
    }

    public static <T> T create(Class<T> aiService, ChatModel chatModel) {
        return builder(aiService)
                .chatLanguageModel(chatModel)
                .build();
    }

    public static <T> AiServices<T> builder(Class<T> aiService) {
        AiServiceContext context = new AiServiceContext(aiService);
        Collection<AiServicesFactory> aiServicesFactories = ServiceHelper.loadFactories(AiServicesFactory.class);
        for (AiServicesFactory factory : aiServicesFactories) {
            return factory.create(context);
        }
        // fallback to default
        return new DefaultAiServices<>(context);
    }


    public AiServices<T> chatLanguageModel(ChatModel chatModel) {
        context.chatModel = chatModel;
        return this;
    }

    public AiServices<T> chatMemory(ChatMemoryStore chatMemoryStore) {
        context.chatMemoryStore = chatMemoryStore;
        return this;
    }

    public AiServices<T> chatMemoryProvider(ChatHistoryRecorder chatHistoryRecorder) {
        context.chatHistoryRecorder = chatHistoryRecorder;
        return this;
    }

    public AiServices<T> tools(Object... objectsWithTools) {
        return tools(Arrays.asList(objectsWithTools));
    }

    /**
     * Constructs and returns the AI Service.
     *
     * @return An instance of the AI Service implementing the specified interface.
     */
    public abstract T build();

    protected void performBasicValidation() {
        if (context.chatModel == null) {
            throw new RuntimeException("Please specify either chatLanguageModel or streamingChatLanguageModel");
        }

        if ((context.chatMemoryStore != null && context.chatHistoryRecorder == null) ||
                (context.chatMemoryStore == null && context.chatHistoryRecorder != null)) {
            throw new RuntimeException(
                    "Please set up chatMemory or chatMemoryProvider in order to use tools. "
                            + "A ChatMemory that can hold at least 3 messages is required for the tools to work properly. "
                            + "While the LLM can technically execute a tool without chat memory, if it only receives the " +
                            "result of the tool's execution without the initial message from the user, it won't interpret " +
                            "the result properly."
            );
        }
    }

}
