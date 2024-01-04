package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.interfaces.message.ChatMessage;
import com.ai.interfaces.model.Model;
import com.ai.openAi.endPoint.chat.msg.DefaultMessage;
import com.ai.openAi.endPoint.chat.req.DefaultChatCompletionRequest;
import com.ai.openAi.endPoint.chat.resp.ChatCompletionResponse;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.memory.chat.message.OpenaiMessageFactory;
import com.ai.openai.param.OpenaiChatModelParameter;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotEmpty;
import static com.ai.openAi.common.Constants.NULL;

/**
 * 对话聊天模型
 **/
@Data
public class OpenaiChatModel implements Model<String, ChatCompletionResponse> {

    private OpenaiChatModelParameter parameter;

    public OpenaiChatModel() {
        this(new OpenaiChatModelParameter());
    }

    public OpenaiChatModel(OpenaiChatModelParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public ChatCompletionResponse generate(String message) {
        ensureNotBlank(message, "chat message");
        return this.generate(Collections.singletonList(OpenaiMessageFactory
                .createChatMessage(message, OpenaiMessageFactory.MessageType.USER)));
    }

    public ChatCompletionResponse generate(List<ChatMessage> messageList) {
        ensureNotEmpty(messageList, "chat message-list");
        ArrayList<DefaultMessage> defaultMessages = new ArrayList<>();
        for (ChatMessage msg : messageList) {
            defaultMessages.add(DefaultMessage.builder().role(msg.type()).content(msg.content()).build());
        }
        DefaultChatCompletionRequest defaultChatCompletionRequest = DefaultChatCompletionRequest.builder().messages(defaultMessages).build();
        BeanUtil.copyProperties(parameter.getParameter(), defaultChatCompletionRequest);
        return OpenAiClient.getAggregationSession().getChatSession().chatCompletions(NULL, NULL, NULL, defaultChatCompletionRequest);
    }

}
