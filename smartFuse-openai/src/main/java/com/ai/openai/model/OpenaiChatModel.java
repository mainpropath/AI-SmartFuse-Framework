package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.interfaces.model.ChatLanguageModel;
import com.ai.openAi.endPoint.chat.req.DefaultChatCompletionRequest;
import com.ai.openAi.endPoint.chat.resp.ChatCompletionResponse;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.param.OpenaiChatModelParameter;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ai.openAi.common.Constants.NULL;

/**
 * @Description: 对话聊天模型
 **/
@Data
@NoArgsConstructor
public class OpenaiChatModel implements ChatLanguageModel<ChatCompletionResponse> {

    private OpenaiChatModelParameter parameter;

    @Override
    public ChatCompletionResponse generate(String userMessage) {
        DefaultChatCompletionRequest defaultChatCompletionRequest = DefaultChatCompletionRequest.BuildDefaultChatCompletionRequest(userMessage);
        BeanUtil.copyProperties(parameter.getParameter(), defaultChatCompletionRequest);
        System.out.println(defaultChatCompletionRequest);
        System.out.println(defaultChatCompletionRequest.getModel());
        return OpenAiClient.getAggregationSession().getChatSession().chatCompletions(NULL, NULL, NULL, defaultChatCompletionRequest);
    }

}
