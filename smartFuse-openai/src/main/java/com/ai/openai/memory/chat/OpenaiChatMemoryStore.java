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

    private final Map<String, List<ChatMessage>> messagesByMemoryId = new ConcurrentHashMap<>();

    public List<ChatMessage> getMessages(String memoryId) {
        return this.messagesByMemoryId.computeIfAbsent(memoryId, (ignored) -> new ArrayList());
    }

    public void updateMessages(String memoryId, List<ChatMessage> messages) {
        this.messagesByMemoryId.put(memoryId, messages);
    }

    public void deleteMessages(String memoryId) {
        this.messagesByMemoryId.remove(memoryId);
    }


}
