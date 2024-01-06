package com.ai.domain.memory.embedding;


import com.ai.domain.data.embedding.Embedding;

import java.util.List;

/**
 * 嵌入数据检索器
 */
public interface EmbeddingStoreRetriever<Metadata> {

    public List<Metadata> findRelevant(Embedding embedding, int maxResults, double minScore);

    public List<Metadata> findRelevant(Embedding embedding);

}
