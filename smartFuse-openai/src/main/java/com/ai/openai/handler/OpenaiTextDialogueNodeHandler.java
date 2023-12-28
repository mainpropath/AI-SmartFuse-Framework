package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.chat.ChatChoice;
import com.ai.openai.model.OpenaiChatModel;

import java.util.List;

/**
 * @Description: 文本对话节点
 **/
public class OpenaiTextDialogueNodeHandler implements ChainNodeHandler<String, String> {

    private String msg;

    private OpenaiChatModel chatModel;

    public OpenaiTextDialogueNodeHandler() {
        this(null, new OpenaiChatModel());
    }

    public OpenaiTextDialogueNodeHandler(String msg) {
        this(msg, new OpenaiChatModel());
    }

    public OpenaiTextDialogueNodeHandler(String msg, OpenaiChatModel chatModel) {
        this.msg = msg;
        this.chatModel = chatModel;
    }

    public String execute() {
        return execute(msg);
    }

    @Override
    public String execute(String parameter) {
        List<ChatChoice> choices = chatModel.generate(parameter).getChoices();
        return choices.get(choices.size() - 1).getMessage().getContent();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OpenaiChatModel getChatModel() {
        return chatModel;
    }

    public void setChatModel(OpenaiChatModel chatModel) {
        this.chatModel = chatModel;
    }

}
