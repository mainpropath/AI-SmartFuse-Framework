package com.ai.openai.converter;

import com.ai.common.usage.TokenUsage;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.data.images.Image;
import com.ai.openAi.common.Usage;
import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import com.ai.openAi.endPoint.images.ImageObject;

import java.util.List;
import java.util.stream.Collectors;

public class BeanConverter {

    public static Embedding embeddingObj2Embedding(EmbeddingObject embeddingObject) {
        return new Embedding(embeddingObject.getEmbedding(), embeddingObject.getContent());
    }

    public static List<Embedding> embeddingObjList2embeddingList(List<EmbeddingObject> embeddingObjects) {
        return embeddingObjects.stream()
                .map(embeddingObject -> embeddingObj2Embedding(embeddingObject))
                .collect(Collectors.toList());
    }

    public static TokenUsage usage2tokenUsage(Usage usage) {
        return new TokenUsage(usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
    }

    public static Image ImageObj2Image(ImageObject imageObject) {
        return new Image(imageObject.getUrl());
    }


}
