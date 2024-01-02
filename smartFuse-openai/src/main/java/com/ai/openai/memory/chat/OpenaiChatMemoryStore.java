package com.ai.openai.memory.chat;

import com.ai.interfaces.memory.chat.ChatMemoryStore;
import com.ai.interfaces.message.ChatMessage;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 存放历史信息
 **/
@NoArgsConstructor
public class OpenaiChatMemoryStore implements ChatMemoryStore {

    /**
     * 保证线程安全
     */
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
