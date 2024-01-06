package com.ai.domain.data.embedding;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 匹配完成存放信息类
 */
@Data
@AllArgsConstructor
public class EmbeddingMatch {

    private final Double score;
    private final Embedding embedding;

}
