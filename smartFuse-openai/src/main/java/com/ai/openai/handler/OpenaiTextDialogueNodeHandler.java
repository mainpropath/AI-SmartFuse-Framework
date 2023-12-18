package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.chat.ChatChoice;
import com.ai.openai.model.OpenaiChatModel;
import com.ai.openai.param.OpenaiChatModelParameter;

import java.util.List;

/**
 * @Description: 文本对话节点
 **/
public class OpenaiTextDialogueNodeHandler implements ChainNodeHandler<String, String> {

    private String msg;

    private OpenaiChatModel chatModel;

    private OpenaiChatModelParameter parameter;

    public OpenaiTextDialogueNodeHandler() {
        this(null, new OpenaiChatModel(), new OpenaiChatModelParameter());
    }

    public OpenaiTextDialogueNodeHandler(String msg) {
        this(msg, new OpenaiChatModel(), new OpenaiChatModelParameter());
    }

    public OpenaiTextDialogueNodeHandler(String msg, OpenaiChatModelParameter parameter) {
        this(msg, new OpenaiChatModel(), parameter);
    }

    public OpenaiTextDialogueNodeHandler(String msg, OpenaiChatModel chatModel, OpenaiChatModelParameter parameter) {
        this.msg = msg;
        this.chatModel = chatModel;
        this.parameter = parameter;
        update();
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
        update();
    }

    public OpenaiChatModelParameter getParameter() {
        return parameter;
    }

    public void setParameter(OpenaiChatModelParameter parameter) {
        this.parameter = parameter;
        update();
    }

    private void update() {
        this.chatModel.setParameter(parameter);
    }
}
