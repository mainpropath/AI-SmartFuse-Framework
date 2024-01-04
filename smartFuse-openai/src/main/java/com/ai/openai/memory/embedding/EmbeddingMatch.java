package com.ai.openai.memory.embedding;


import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 匹配完成存放信息类
 */
@Data
@AllArgsConstructor
public class EmbeddingMatch<Metadata> {

    private final Double score;
    private final String id;
    private final EmbeddingObject embedding;
    private final Metadata metadata;

}
