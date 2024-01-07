package com.ai.openai.memory.embedding;


import com.ai.domain.data.embedding.CosineSimilarity;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.data.embedding.EmbeddingMatch;
import com.ai.domain.memory.embedding.EmbeddingMemoryStore;
import com.ai.domain.memory.embedding.EmbeddingStoreRetriever;
import lombok.Builder;

import java.util.*;

import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static java.util.Comparator.comparingDouble;

/**
 * 嵌入数据检索器
 */
@Builder
public class OpenaiEmbeddingStoreRetriever implements EmbeddingStoreRetriever<EmbeddingMatch> {

    @Builder.Default
    private final EmbeddingMemoryStore<Embedding> embeddingMemoryStore = new OpenaiEmbeddingMemoryStore();
    @Builder.Default
    private final int maxResults = 2;
    @Builder.Default
    private final Double minScore = 0.7;

    public List<EmbeddingMatch> findRelevant(Embedding embedding, int maxResults, double minScore) {
        ensureNotNull(embedding, "embedding");
        Comparator<EmbeddingMatch> comparator = comparingDouble(EmbeddingMatch::getScore);
        PriorityQueue<EmbeddingMatch> matches = new PriorityQueue<>(comparator);
        List<Embedding> allData = embeddingMemoryStore.getAllData();
        for (Embedding data : allData) {
            double cosineSimilarity = CosineSimilarity.between(data, embedding);
            double score = CosineSimilarity.fromCosineSimilarity(cosineSimilarity);
            if (score >= minScore) {
                matches.add(new EmbeddingMatch(score, data));
                if (matches.size() > maxResults) {
                    matches.poll();
                }
            }
        }
        ArrayList<EmbeddingMatch> result = new ArrayList<>(matches);
        result.sort(comparator);
        Collections.reverse(result);
        return result;
    }

    public List<EmbeddingMatch> findRelevant(Embedding embedding) {
        ensureNotNull(embedding, "embedding");
        return findRelevant(embedding, this.maxResults, this.minScore);
    }


}
