package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.document.TextSegment;
import com.ai.domain.model.EmbeddingModel;
import com.ai.openai.achieve.standard.session.EmbeddingSession;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.embeddings.EmbeddingObject;
import com.ai.openai.endPoint.embeddings.req.EmbeddingCompletionRequest;
import com.ai.openai.endPoint.embeddings.resp.EmbeddingCompletionResponse;
import com.ai.openai.parameter.OpenaiEmbeddingModelParameter;
import com.ai.openai.parameter.input.OpenaiEmbeddingParameter;

import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.*;
import static com.ai.core.exception.Constants.NULL;
import static com.ai.openai.converter.BeanConverter.usage2tokenUsage;

/**
 * 文本嵌入模型
 **/
public class OpenaiEmbeddingModel implements EmbeddingModel {

    private final EmbeddingSession embeddingSession = OpenAiClient.getAggregationSession().getEmbeddingSession();
    private Parameter<OpenaiEmbeddingParameter> parameter;

    public OpenaiEmbeddingModel() {
        this.parameter = new OpenaiEmbeddingModelParameter();
    }

    public OpenaiEmbeddingModel(Parameter<OpenaiEmbeddingParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    public static Embedding embeddingObj2Embedding(EmbeddingObject embeddingObject) {
        return new Embedding(embeddingObject.getEmbedding(), embeddingObject.getContent());
    }

    public static List<Embedding> embeddingObjList2embeddingList(List<EmbeddingObject> embeddingObjects) {
        return embeddingObjects.stream()
                .map(embeddingObject -> embeddingObj2Embedding(embeddingObject))
                .collect(Collectors.toList());
    }

    public Parameter<OpenaiEmbeddingParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<OpenaiEmbeddingParameter> parameter) {
        this.parameter = parameter;
    }

    @Override
    public AiResponse<Embedding> embed(String text) {
        ensureNotBlank(text, "text");
        EmbeddingCompletionResponse response = OpenAiClient.getAggregationSession().getEmbeddingSession().embeddingCompletions(NULL, NULL, NULL, text);
        Embedding embedding = embeddingObj2Embedding(response.getData().get(0));
        return AiResponse.R(embedding, usage2tokenUsage(response.getUsage()), FinishReason.success());
    }

    public AiResponse<List<Embedding>> embed(List<String> stringList) {
        ensureNotEmpty(stringList, "stringList");
        // 发起请求
        EmbeddingCompletionResponse embeddingCompletionResponse = embeddingSession.embeddingCompletions(NULL, NULL, NULL, createRequestParameter(stringList));
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
        return AiResponse.R(embeddings, usage2tokenUsage(response.getUsage()), FinishReason.success());
    }

    private EmbeddingCompletionRequest createRequestParameter(List<String> stringList) {
        EmbeddingCompletionRequest request = EmbeddingCompletionRequest.baseBuild(stringList);
        BeanUtil.copyProperties(parameter.getParameter(), request);
        return request;
    }

    private List<String> textSegmentList2StringList(List<TextSegment> textSegments) {
        return textSegments.stream()
                .map(TextSegment::getText).collect(Collectors.toList());
    }
}
