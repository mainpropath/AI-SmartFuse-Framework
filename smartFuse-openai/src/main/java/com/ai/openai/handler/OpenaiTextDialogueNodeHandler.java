package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.interfaces.message.ChatMessage;
import com.ai.openAi.endPoint.chat.ChatChoice;
import com.ai.openai.memory.message.OpenaiAssistantMessage;
import com.ai.openai.model.OpenaiChatModel;

import java.util.List;

/**
 * @Description: 文本对话节点
 **/
public class OpenaiTextDialogueNodeHandler implements ChainNodeHandler<List<ChatMessage>, OpenaiAssistantMessage> {

    private List<ChatMessage> msgList;

    private OpenaiChatModel chatModel;

    public OpenaiTextDialogueNodeHandler() {
        this(null, new OpenaiChatModel());
    }

    public OpenaiTextDialogueNodeHandler(List<ChatMessage> msgList) {
        this(msgList, new OpenaiChatModel());
    }

    public OpenaiTextDialogueNodeHandler(List<ChatMessage> msgList, OpenaiChatModel chatModel) {
        this.msgList = msgList;
        this.chatModel = chatModel;
    }

    public OpenaiAssistantMessage execute() {
        return execute(msgList);
    }

    @Override
    public OpenaiAssistantMessage execute(List<ChatMessage> msgList) {
        if (msgList == null || msgList.isEmpty()) {
            throw new RuntimeException("Handler request parameter is empty.");
        }
        List<ChatChoice> choices = chatModel.generate(msgList).getChoices();
        return OpenaiAssistantMessage.builder().content(choices.get(choices.size() - 1).getMessage().getContent()).build();
    }

    public List<ChatMessage> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<ChatMessage> msgList) {
        this.msgList = msgList;
    }

    public OpenaiChatModel getChatModel() {
        return chatModel;
    }

    public void setChatModel(OpenaiChatModel chatModel) {
        this.chatModel = chatModel;
    }

}
