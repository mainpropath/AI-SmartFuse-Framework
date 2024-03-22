package com.ai.domain.data.embedding;


import com.ai.common.util.Exceptions;
import com.ai.common.util.ValidationUtils;

/**
 * 相关性分析
 */
public class CosineSimilarity {

    public static double between(Embedding embeddingA, Embedding embeddingB) {
        ValidationUtils.ensureNotNull(embeddingA, "embeddingA");
        ValidationUtils.ensureNotNull(embeddingB, "embeddingB");
        double[] vectorA = embeddingA.getEmbedding();
        double[] vectorB = embeddingB.getEmbedding();
        if (vectorA.length != vectorB.length) {
            throw Exceptions.illegalArgument("Length of vector a (%s) must be equal to the length of vector b (%s)", new Object[]{vectorA.length, vectorB.length});
        } else {
            double dotProduct = 0.0D;
            double normA = 0.0D;
            double normB = 0.0D;

            for (int i = 0; i < vectorA.length; ++i) {
                dotProduct += (vectorA[i] * vectorB[i]);
                normA += (vectorA[i] * vectorA[i]);
                normB += (vectorB[i] * vectorB[i]);
            }

            return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        }
    }

    public static double fromCosineSimilarity(double cosineSimilarity) {
        return (cosineSimilarity + 1.0D) / 2.0D;
    }

    public static double fromRelevanceScore(double relevanceScore) {
        return relevanceScore * 2.0D - 1.0D;
    }

}
