package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.data.parameter.Parameter;
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

import static com.ai.common.util.ValidationUtils.ensureNotEmpty;
import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.core.exception.Constants.NULL;
import static com.ai.openai.converter.BeanConverter.usage2tokenUsage;

/**
 * 文本嵌入
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

    public static List<Embedding> embeddingObjList2embeddingList(List<EmbeddingObject> embeddingObjectList) {
        return embeddingObjectList.stream()
                .map(embeddingObject -> embeddingObj2Embedding(embeddingObject))
                .collect(Collectors.toList());
    }

    public Parameter<OpenaiEmbeddingParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<OpenaiEmbeddingParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    public AiResponse<List<Embedding>> embed(List<String> stringList) {
        ensureNotEmpty(stringList, "stringList");
        // 构造请求主要参数
        EmbeddingCompletionRequest request = EmbeddingCompletionRequest.baseBuild(stringList);
        // 填充请求配置属性
        BeanUtil.copyProperties(parameter.getParameter(), request);
        // 发起请求获取结果
        EmbeddingCompletionResponse response = embeddingSession.embeddingCompletions(NULL, NULL, NULL, request);
        // 转换结果为统一返回值
        List<Embedding> embeddings = embeddingObjList2embeddingList(response.getData());
        return AiResponse.R(embeddings, usage2tokenUsage(response.getUsage()), FinishReason.success());
    }
}
