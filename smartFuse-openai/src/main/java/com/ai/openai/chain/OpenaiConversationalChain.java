package com.ai.openai.chain;

import com.ai.interfaces.chain.Chain;
import com.ai.openai.handler.OpenaiTextDialogueNodeHandler;
import com.ai.openai.memory.OpenaiChatHistoryRecorder;
import com.ai.openai.memory.message.OpenaiAssistantMessage;
import com.ai.openai.memory.message.OpenaiUserMessage;
import lombok.Builder;
import lombok.Data;

/**
 * @Description: 纯文本聊天链
 **/
@Data
@Builder
public class OpenaiConversationalChain implements Chain<String, String> {

    @Builder.Default
    private OpenaiTextDialogueNodeHandler textDialogueNodeHandler = new OpenaiTextDialogueNodeHandler();

    @Builder.Default
    private OpenaiChatHistoryRecorder historyRecorder = OpenaiChatHistoryRecorder.builder().build();

    @Override
    public String run(String s) {
        historyRecorder.add(OpenaiUserMessage.builder().content(s).build());
        OpenaiAssistantMessage openaiAssistantMessage = textDialogueNodeHandler.execute(historyRecorder.getCurrentMessages());
        historyRecorder.add(openaiAssistantMessage);
        return openaiAssistantMessage.content();
    }
}
