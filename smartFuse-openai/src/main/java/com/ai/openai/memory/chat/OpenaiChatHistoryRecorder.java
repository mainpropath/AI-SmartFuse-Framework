package com.ai.openai.memory.chat;

import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.message.SystemMessage;
import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.memory.chat.ChatMemoryStore;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ai.common.util.Utils.randomUUID;

/**
 * Openai历史信息记录器
 */
@Builder
public class OpenaiChatHistoryRecorder implements ChatHistoryRecorder {

    @Builder.Default
    private final String id = randomUUID();
    @Builder.Default
    private final Integer maxMessageNumber = 30;
    @Builder.Default
    private final ChatMemoryStore memoryStore = new OpenaiChatMemoryStore();

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public void add(ChatMessage message) {
        List<ChatMessage> messages = this.getCurrentMessages();
        if (message instanceof SystemMessage) {
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
        List<ChatMessage> messages = new ArrayList(this.memoryStore.getMessages(this.id));
        updatePolicy(messages);
        return messages;
    }

    @Override
    public void clear() {
        this.memoryStore.deleteMessages(this.id);
    }

}
