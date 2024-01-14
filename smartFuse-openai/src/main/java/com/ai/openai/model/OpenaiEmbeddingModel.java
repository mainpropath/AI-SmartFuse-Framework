package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.common.resp.usage.TokenUsage;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.document.TextSegment;
import com.ai.domain.model.EmbeddingModel;
import com.ai.openAi.endPoint.embeddings.req.EmbeddingCompletionRequest;
import com.ai.openAi.endPoint.embeddings.resp.EmbeddingCompletionResponse;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.parameter.OpenaiEmbeddingModelParameter;
import com.ai.openai.parameter.input.OpenaiEmbeddingParameter;

import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.*;
import static com.ai.openAi.common.Constants.NULL;
import static com.ai.openai.converter.BeanConverter.*;

/**
 * 文本嵌入模型
 **/
public class OpenaiEmbeddingModel implements EmbeddingModel {

    private Parameter<OpenaiEmbeddingParameter> parameter;

    public OpenaiEmbeddingModel() {
        this.parameter = new OpenaiEmbeddingModelParameter();
    }

    public OpenaiEmbeddingModel(Parameter<OpenaiEmbeddingParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<Embedding> embed(String text) {
        ensureNotBlank(text, "text");
        EmbeddingCompletionResponse response = OpenAiClient.getAggregationSession().getEmbeddingSession().embeddingCompletions(NULL, NULL, NULL, text);
        Embedding embedding = embeddingObj2Embedding(response.getData().get(0));
        TokenUsage tokenUsage = usage2tokenUsage(response.getUsage());
        return new AiResponse<>(embedding, tokenUsage, FinishReason.SUCCESS);
    }

    public AiResponse<List<Embedding>> embed(List<String> stringList) {
        ensureNotEmpty(stringList, "stringList");
        // 发起请求
        EmbeddingCompletionResponse embeddingCompletionResponse = OpenAiClient
                .getAggregationSession()
                .getEmbeddingSession()
                .embeddingCompletions(NULL, NULL, NULL, createRequestParameter(stringList));
        return createAiResponse(embeddingCompletionResponse);
    }

    @Override
    public AiResponse<Embedding> embed(TextSegment textSegment) {
        ensureNotNull(textSegment, "textSegment");
        return embed(textSegment.text());
    }

    @Override
    public AiResponse<List<Embedding>> embedAll(List<TextSegment> textSegments) {
        ensureNotEmpty(textSegments, "textSegments");
        return embed(textSegmentList2StringList(textSegments));
    }

    private AiResponse<List<Embedding>> createAiResponse(EmbeddingCompletionResponse response) {
        List<Embedding> embeddings = embeddingObjList2embeddingList(response.getData());
        TokenUsage tokenUsage = usage2tokenUsage(response.getUsage());
        return new AiResponse<>(embeddings, tokenUsage, FinishReason.SUCCESS);
    }

    private EmbeddingCompletionRequest createRequestParameter(List<String> stringList) {
        EmbeddingCompletionRequest request = EmbeddingCompletionRequest.BuildBaseEmbeddingCompletionRequest(stringList);
        BeanUtil.copyProperties(parameter.getParameter(), request);
        return request;
    }

    private List<String> textSegmentList2StringList(List<TextSegment> textSegments) {
        return textSegments.stream()
                .map(TextSegment::getText).collect(Collectors.toList());
    }
}
