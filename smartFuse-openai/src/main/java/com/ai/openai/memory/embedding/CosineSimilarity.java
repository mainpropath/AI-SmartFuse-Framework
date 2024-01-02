package com.ai.openai.memory.embedding;


import com.ai.openAi.endPoint.embeddings.EmbeddingObject;

import java.math.BigDecimal;
import java.util.List;

import static com.ai.common.util.Exceptions.illegalArgument;
import static com.ai.common.util.ValidationUtils.ensureNotNull;

public class CosineSimilarity {

    public static double between(EmbeddingObject embeddingA, EmbeddingObject embeddingB) {
        ensureNotNull(embeddingA, "embeddingA");
        ensureNotNull(embeddingB, "embeddingB");

        List<BigDecimal> dataA = embeddingA.getEmbedding();
        List<BigDecimal> dataB = embeddingB.getEmbedding();
        int sizeA = dataA.size();
        int sizeB = dataB.size();
        if (sizeA != sizeB) {
            throw illegalArgument("Length of embedding list a (%s) must be equal to the length of embedding list b (%s)", sizeA, sizeB);
        }

        BigDecimal dotProduct = new BigDecimal(0);
        BigDecimal normA = new BigDecimal(0);
        BigDecimal normB = new BigDecimal(0);

        for (int i = 0; i < sizeA; i++) {
            BigDecimal numberA = dataA.get(i);
            BigDecimal numberB = dataB.get(i);
            dotProduct = dotProduct.add(numberA.multiply(numberB));
            normA = normA.add(numberA.multiply(numberA));
            normB = normB.add(numberB.multiply(numberB));
        }
        return dotProduct.doubleValue() / Math.sqrt(normA.doubleValue()) * Math.sqrt(normB.doubleValue());
    }

    public static double fromCosineSimilarity(double cosineSimilarity) {
        return (cosineSimilarity + 1) / 2;
    }

}
