package com.ai.domain.memory.chat.impl;

import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.message.SystemMessage;
import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.memory.chat.ChatMemoryStore;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ai.common.util.Utils.randomUUID;


@Data
@Builder
public class SimpleChatHistoryRecorder implements ChatHistoryRecorder {

    @Builder.Default
    private String id = randomUUID();
    @Builder.Default
    private Integer maxMessageNumber = 100;
    @Builder.Default
    private ChatMemoryStore memoryStore = new SimpleChatMemoryStore();

    public SimpleChatHistoryRecorder() {
        this(randomUUID(), 30, new SimpleChatMemoryStore());
    }

    public SimpleChatHistoryRecorder(String id, Integer maxMessageNumber, ChatMemoryStore memoryStore) {
        this.id = id;
        this.maxMessageNumber = maxMessageNumber;
        this.memoryStore = memoryStore;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void add(ChatMessage message) {
        List<ChatMessage> messages = this.getCurrentMessages();
        // 如果添加的是系统消息
        if (message instanceof SystemMessage) {
            // 先判断是否有相同的系统消息
            Optional<SystemMessage> systemMessage = findSystemMessage(messages);
            if (systemMessage.isPresent()) {
                if (systemMessage.get().equals(message)) {
                    return;
                }
                messages.remove(systemMessage.get());
            }
        }
        messages.add(message);
        updatePolicy(messages);
        memoryStore.updateMessages(this.id, messages);
    }

    /**
     * 查找到对应的系统消息
     */
    private Optional<SystemMessage> findSystemMessage(List<ChatMessage> messages) {
        return messages.stream()
                .filter((message) -> message instanceof SystemMessage)
                .map((message) -> (SystemMessage) message)
                .findAny();
    }

    private void updatePolicy(List<ChatMessage> messages) {
        while (messages.size() > maxMessageNumber) {
            int messageToRemove = 0;
            if (messages.get(0) instanceof SystemMessage) {
                messageToRemove = 1;
            }
            messages.remove(messageToRemove);
        }
    }

    @Override
    public List<ChatMessage> getCurrentMessages() {
        return getMessagesById(this.id);
    }

    @Override
    public List<ChatMessage> getMessagesById(String id) {
        List<ChatMessage> messages = new ArrayList(this.memoryStore.getMessages(id));
        updatePolicy(messages);
        return messages;
    }

    @Override
    public void clearById(String id) {
        this.memoryStore.deleteMessages(id);
    }

    @Override
    public void clear() {
        this.clearById(this.id);
    }

}
