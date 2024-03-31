package com.ai.domain.chain.impl;

import com.ai.domain.chain.Chain;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.UserMessage;
import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.model.ChatModel;
import lombok.Builder;
import lombok.Data;

/**
 * 纯文本聊天链
 **/
@Data
@Builder
public class ConversationalChain implements Chain<String, String> {

    private ChatModel chatModel;
    private ChatHistoryRecorder historyRecorder;

    @Override
    public String run(String s) {
        historyRecorder.add(UserMessage.message(s));
        AssistantMessage data = chatModel.generate(historyRecorder.getCurrentMessages()).getData();
        historyRecorder.add(data);
        return data.text();
    }
}
