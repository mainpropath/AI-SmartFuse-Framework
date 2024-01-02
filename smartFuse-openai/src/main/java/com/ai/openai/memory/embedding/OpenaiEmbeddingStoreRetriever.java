package com.ai.openai.memory.embedding;


import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import lombok.Builder;

import java.util.*;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.openai.handler.OpenaiEmbeddingNodeHandler.executeByStatic;
import static java.util.Comparator.comparingDouble;

@Builder
public class OpenaiEmbeddingStoreRetriever<Metadata> {

    @Builder.Default
    private final OpenaiEmbeddingMemoryStore<Metadata> openaiEmbeddingMemoryStore = new OpenaiEmbeddingMemoryStore<>();
    @Builder.Default
    private final int maxResults = 2;
    @Builder.Default
    private final Double minScore = 0.5;

    public List<EmbeddingMatch<Metadata>> findRelevant(EmbeddingObject embeddingObject, int maxResults, double minScore) {
        ensureNotNull(embeddingObject, "embeddingObject");
        Comparator<EmbeddingMatch<Metadata>> comparator = comparingDouble(EmbeddingMatch::getScore);
        PriorityQueue<EmbeddingMatch<Metadata>> matches = new PriorityQueue<>(comparator);
        openaiEmbeddingMemoryStore.getIdToEmbeddingData().forEach((key, value) -> {
            double cosineSimilarity = CosineSimilarity.between(value.getEmbedding(), embeddingObject);
            double score = CosineSimilarity.fromCosineSimilarity(cosineSimilarity);
            if (score >= minScore) {
                matches.add(new EmbeddingMatch<>(score, value.getId(), value.getEmbedding(), value.getMetadata()));
                if (matches.size() > maxResults) {
                    matches.poll();
                }
            }
        });
        ArrayList<EmbeddingMatch<Metadata>> result = new ArrayList<>(matches);
        result.sort(comparator);
        Collections.reverse(result);
        return result;
    }

    public List<EmbeddingMatch<Metadata>> findRelevant(EmbeddingObject embeddingObject) {
        ensureNotNull(embeddingObject, "embeddingObject");
        return findRelevant(embeddingObject, this.maxResults, this.minScore);
    }

    public List<EmbeddingMatch<Metadata>> findRelevant(String text, int maxResults, double minScore) {
        ensureNotBlank(text, "text");
        EmbeddingObject embeddingObject = executeByStatic(text);
        return findRelevant(embeddingObject, maxResults, minScore);
    }

    public List<EmbeddingMatch<Metadata>> findRelevant(String text) {
        ensureNotBlank(text, "text");
        return findRelevant(text, this.maxResults, this.minScore);
    }

}
