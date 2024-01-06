package com.ai.domain.data.embedding;


import java.math.BigDecimal;
import java.util.List;

import static com.ai.common.util.Exceptions.illegalArgument;
import static com.ai.common.util.ValidationUtils.ensureNotNull;

/**
 * 相关性分析
 */
public class CosineSimilarity {

    public static double between(Embedding embeddingA, Embedding embeddingB) {
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
