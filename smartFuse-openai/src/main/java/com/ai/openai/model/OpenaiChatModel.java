package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.interfaces.message.ChatMessage;
import com.ai.interfaces.model.ChatLanguageModel;
import com.ai.openAi.endPoint.chat.msg.DefaultMessage;
import com.ai.openAi.endPoint.chat.req.DefaultChatCompletionRequest;
import com.ai.openAi.endPoint.chat.resp.ChatCompletionResponse;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.memory.message.OpenaiMessageFactory;
import com.ai.openai.param.OpenaiChatModelParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ai.openAi.common.Constants.NULL;

/**
 * @Description: 对话聊天模型
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenaiChatModel implements ChatLanguageModel<ChatCompletionResponse> {

    private OpenaiChatModelParameter parameter;

    @Override
    public ChatCompletionResponse generate(String userMessage) {
        return this.generate(Collections.singletonList(OpenaiMessageFactory
                .createChatMessage(userMessage, OpenaiMessageFactory.MessageType.USER)));
    }

    @Override
    public ChatCompletionResponse generate(List<ChatMessage> messageList) {
        ArrayList<DefaultMessage> defaultMessages = new ArrayList<>();
        for (ChatMessage msg : messageList) {
            defaultMessages.add(DefaultMessage.builder().role(msg.type()).content(msg.content()).build());
        }
        DefaultChatCompletionRequest defaultChatCompletionRequest = DefaultChatCompletionRequest.builder().messages(defaultMessages).build();
        BeanUtil.copyProperties(parameter.getParameter(), defaultChatCompletionRequest);
        return OpenAiClient.getAggregationSession().getChatSession().chatCompletions(NULL, NULL, NULL, defaultChatCompletionRequest);
    }

}
