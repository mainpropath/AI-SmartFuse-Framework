package com.ai.baidu.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.baidu.achieve.standard.session.ChatSession;
import com.ai.baidu.client.BaiduClient;
import com.ai.baidu.converter.BeanConverter;
import com.ai.baidu.endPoint.chat.Message;
import com.ai.baidu.endPoint.chat.req.ChatRequest;
import com.ai.baidu.endPoint.chat.resp.ChatResponse;
import com.ai.baidu.parameter.BaiduChatModelParameter;
import com.ai.baidu.parameter.input.BaiduChatParameter;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.common.resp.usage.TokenUsage;
import com.ai.common.util.Exceptions;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.message.MessageType;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ChatModel;

import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.ensureNotEmpty;
import static com.ai.common.util.ValidationUtils.ensureNotNull;


public class BaiduChatModel implements ChatModel {

    private final ChatSession chatSession = BaiduClient.getAggregationSession().getChatSession();
    private Parameter<BaiduChatParameter> parameter;

    public BaiduChatModel() {
        this(new BaiduChatModelParameter());
    }

    public BaiduChatModel(Parameter<BaiduChatParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    public static List<Message> chatMessageList2BaiduMessageList(List<ChatMessage> chatMessages) {
        return chatMessages.stream().map(chatMessage -> {
            // 百度不支持System类型的消息
            if (chatMessage.type().getMessageType().equals(MessageType.SYSTEM))
                throw Exceptions.runtime(String.format("Baidu API does not support [%s] type messages", MessageType.SYSTEM.getMessageType()));
            return Message.builder().role(chatMessage.type().getMessageType()).content(chatMessage.text()).build();
        }).collect(Collectors.toList());
    }

    public Parameter<BaiduChatParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<BaiduChatParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<AssistantMessage> generate(List<ChatMessage> messages) {
        ensureNotEmpty(messages, "messages");
        // 将消息转换为百度格式的消息
        List<Message> messageList = chatMessageList2BaiduMessageList(messages);
        // 构造请求主要参数
        ChatRequest chatRequest = ChatRequest.builder().messages(messageList).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(parameter.getParameter(), chatRequest);
        // 发起请求获取结果
        ChatResponse chatResponse = this.chatSession.chat(chatRequest);
        // 转换结果为统一返回值
        AssistantMessage message = AssistantMessage.message(chatResponse.getResult());
        TokenUsage tokenUsage = BeanConverter.usage2tokenUsage(chatResponse.getUsage());
        return AiResponse.R(message, tokenUsage, FinishReason.success());
    }

}
