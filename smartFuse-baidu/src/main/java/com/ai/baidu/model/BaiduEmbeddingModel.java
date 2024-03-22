package com.ai.baidu.model;

import com.ai.baidu.achieve.standard.session.EmbeddingSession;
import com.ai.baidu.client.BaiduClient;
import com.ai.baidu.endPoint.embedding.EmbeddingData;
import com.ai.baidu.endPoint.embedding.req.EmbeddingRequest;
import com.ai.baidu.endPoint.embedding.resp.EmbeddingResponse;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.model.EmbeddingModel;

import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.ensureNotEmpty;


public class BaiduEmbeddingModel implements EmbeddingModel {

    private final EmbeddingSession embeddingSession = BaiduClient.getAggregationSession().getEmbeddingSession();

    public static Embedding embeddingData2Embedding(EmbeddingData embeddingData) {
        return new Embedding(embeddingData.getEmbedding(), embeddingData.getContent());
    }

    public static List<Embedding> embeddingDataList2EmbeddingList(List<EmbeddingData> embeddingDataList) {
        return embeddingDataList.stream()
                .map(EmbeddingData -> embeddingData2Embedding(EmbeddingData))
                .collect(Collectors.toList());
    }

    @Override
    public AiResponse<List<Embedding>> embed(List<String> text) {
        ensureNotEmpty(text, "text");
        EmbeddingResponse embedding = embeddingSession.embedding(EmbeddingRequest.baseBuild(text));
        return AiResponse.R(embeddingDataList2EmbeddingList(embedding.getData()), FinishReason.success());
    }

}
