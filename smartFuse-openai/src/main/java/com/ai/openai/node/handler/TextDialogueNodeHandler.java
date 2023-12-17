package com.ai.openai.node.handler;

import cn.hutool.core.bean.BeanUtil;
import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.chat.req.DefaultChatCompletionRequest;
import com.ai.openAi.endPoint.chat.resp.ChatCompletionResponse;
import com.ai.openai.client.OpenAiClient;

import java.util.Map;

import static com.ai.openAi.common.Constants.NULL;

/**
 * @Description: 文本对话节点
 **/
public class TextDialogueNodeHandler implements ChainNodeHandler<DefaultChatCompletionRequest, ChatCompletionResponse> {

    @Override
    public ChatCompletionResponse execute(Map<String, Object> parameter) {
        return this.execute(convert(parameter));
    }

    @Override
    public ChatCompletionResponse execute(DefaultChatCompletionRequest parameter) {
        return OpenAiClient.getAggregationSession().getChatSession().chatCompletions(NULL, NULL, NULL, parameter);
    }

    @Override
    public DefaultChatCompletionRequest convert(Map<String, Object> parameter) {
        return BeanUtil.fillBeanWithMap(parameter, new DefaultChatCompletionRequest(), false);
    }

}
