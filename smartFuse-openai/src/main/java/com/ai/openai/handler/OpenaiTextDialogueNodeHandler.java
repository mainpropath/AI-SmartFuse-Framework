package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.interfaces.message.ChatMessage;
import com.ai.openAi.endPoint.chat.ChatChoice;
import com.ai.openai.memory.chat.message.OpenaiAssistantMessage;
import com.ai.openai.model.OpenaiChatModel;
import lombok.Data;

import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotEmpty;

/**
 * 文本对话链路节点
 **/
@Data
public class OpenaiTextDialogueNodeHandler implements ChainNodeHandler<List<ChatMessage>, OpenaiAssistantMessage> {

    private OpenaiChatModel chatModel;

    public OpenaiTextDialogueNodeHandler() {
        this(new OpenaiChatModel());
    }

    public OpenaiTextDialogueNodeHandler(OpenaiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public OpenaiAssistantMessage execute(List<ChatMessage> msgList) {
        ensureNotEmpty(msgList, "chat parameter");
        List<ChatChoice> choices = chatModel.generate(msgList).getChoices();
        return OpenaiAssistantMessage.builder().content(choices.get(choices.size() - 1).getMessage().getContent()).build();
    }
}
