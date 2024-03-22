package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ChatModel;
import com.ai.openai.achieve.standard.session.ChatSession;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.chat.ChatChoice;
import com.ai.openai.endPoint.chat.msg.DefaultMessage;
import com.ai.openai.endPoint.chat.req.DefaultChatCompletionRequest;
import com.ai.openai.endPoint.chat.resp.ChatCompletionResponse;
import com.ai.openai.parameter.OpenaiChatModelParameter;
import com.ai.openai.parameter.input.OpenaiChatParameter;

import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.ensureNotEmpty;
import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.core.exception.Constants.NULL;
import static com.ai.openai.converter.BeanConverter.usage2tokenUsage;

/**
 * openai对话聊天模型
 **/
public class OpenaiChatModel implements ChatModel {

    private final ChatSession chatSession = OpenAiClient.getAggregationSession().getChatSession();
    private Parameter<OpenaiChatParameter> parameter;

    public OpenaiChatModel() {
        this(new OpenaiChatModelParameter());
    }

    public OpenaiChatModel(Parameter<OpenaiChatParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    private static List<DefaultMessage> chatMessageList2DefaultMessageList(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .map(chatMessage -> DefaultMessage.builder()
                        .role(chatMessage.type().getMessageType())
                        .content(chatMessage.text())
                        .build())
                .collect(Collectors.toList());
    }

    public Parameter<OpenaiChatParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<OpenaiChatParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<AssistantMessage> generate(List<ChatMessage> messages) {
        ensureNotEmpty(messages, "messages");
        // 将message转换为openai模型所需的格式
        List<DefaultMessage> defaultMessages = chatMessageList2DefaultMessageList(messages);
        // 构造请求主要参数
        DefaultChatCompletionRequest request = DefaultChatCompletionRequest.builder()
                .messages(defaultMessages).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(parameter.getParameter(), request);
        // 发送请求获取结果
        ChatCompletionResponse response = chatSession.chatCompletions(NULL, NULL, NULL, request);
        return createAiResponse(response);
    }

    private AiResponse<AssistantMessage> createAiResponse(ChatCompletionResponse response) {
        // 获取对话内容
        List<ChatChoice> choices = response.getChoices();
        // 得到模型的回复
        AssistantMessage assistantMessage = AssistantMessage.message(choices.get(choices.size() - 1).getMessage().getContent());
        // 转换结果为统一返回值
        return AiResponse.R(assistantMessage, usage2tokenUsage(response.getUsage()), FinishReason.success());
    }

}
