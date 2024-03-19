package com.ai.domain.memory.embedding;


import com.ai.domain.data.embedding.Embedding;

import java.util.List;

/**
 * 嵌入数据检索器
 */
public interface EmbeddingStoreRetriever<Metadata> {

    List<Metadata> findRelevant(Embedding embedding, int maxResults, double minScore);

    List<Metadata> findRelevant(Embedding embedding);

}
