package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.document.TextSegment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.*;

public interface EmbeddingModel {

    /**
     * 对单个文本进行嵌入
     */
    default AiResponse<Embedding> embed(String text) {
        ensureNotBlank(text, "text");
        AiResponse<List<Embedding>> res = embed(Arrays.asList(text));
        return AiResponse.R(res.getData().get(0), res.getTokenUsage(), res.getFinishReason());
    }

    /**
     * 对多个文本进行嵌入
     */
    AiResponse<List<Embedding>> embed(List<String> text);

    /**
     * 对切分的文本段进行嵌入
     */
    default AiResponse<Embedding> embed(TextSegment textSegment) {
        ensureNotNull(textSegment, "textSegment");
        return embed(textSegment.text());
    }

    /**
     * 对多个切分的文本段进行嵌入
     */
    default AiResponse<List<Embedding>> embedAll(List<TextSegment> textSegmentList) {
        ensureNotEmpty(textSegmentList, "textSegments");
        List<String> stringList = textSegmentList.stream()
                .map(TextSegment::text).collect(Collectors.toList());
        return embed(stringList);
    }

}
