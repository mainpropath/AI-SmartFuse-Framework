package com.ai.openai.memory;

import com.ai.interfaces.memory.ChatHistoryRecorder;
import com.ai.interfaces.memory.ChatMemoryStore;
import com.ai.interfaces.message.ChatMessage;
import lombok.Builder;

import java.util.List;

@Builder
public class OpenaiChatHistoryRecorder implements ChatHistoryRecorder {

    private final Object id;
    private final Integer maxMessages;
    private final ChatMemoryStore store;

    @Override
    public Object id() {
        return this.id;
    }

    @Override
    public void add(ChatMessage message) {

    }

    @Override
    public List<ChatMessage> messages() {
        return null;
    }

    @Override
    public void clear() {

    }


}
