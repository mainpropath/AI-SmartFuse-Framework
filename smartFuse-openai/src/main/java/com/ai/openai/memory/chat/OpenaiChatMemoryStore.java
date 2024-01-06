package com.ai.openai.memory.chat;

import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.memory.chat.ChatMemoryStore;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Openai历史信息存储器
 **/
@NoArgsConstructor
public class OpenaiChatMemoryStore implements ChatMemoryStore {

    private final Map<Object, List<ChatMessage>> messagesByMemoryId = new ConcurrentHashMap<>();

    public List<ChatMessage> getMessages(Object memoryId) {
        return this.messagesByMemoryId.computeIfAbsent(memoryId, (ignored) -> new ArrayList());
    }

    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        this.messagesByMemoryId.put(memoryId, messages);
    }

    public void deleteMessages(Object memoryId) {
        this.messagesByMemoryId.remove(memoryId);
    }
}
