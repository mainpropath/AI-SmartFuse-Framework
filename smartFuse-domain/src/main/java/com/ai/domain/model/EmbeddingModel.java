package com.ai.domain.model;


import com.ai.common.usage.AiResponse;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.document.TextSegment;

import java.util.List;

public interface EmbeddingModel {

    AiResponse<Embedding> embed(String text);

    AiResponse<List<Embedding>> embed(List<String> text);

    AiResponse<Embedding> embed(TextSegment textSegment);

    AiResponse<List<Embedding>> embedAll(List<TextSegment> textSegments);

}
