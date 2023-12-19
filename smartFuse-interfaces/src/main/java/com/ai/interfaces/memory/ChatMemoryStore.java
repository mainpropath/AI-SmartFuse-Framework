package com.ai.interfaces.memory;

import com.ai.interfaces.message.ChatMessage;

import java.util.List;

public interface ChatMemoryStore {

    List<ChatMessage> getMessages(Object msg);

    void updateMessages(Object msg, List<ChatMessage> msgList);

    void deleteMessages(Object msg);

}
