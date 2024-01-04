package com.ai.openai.memory.embedding;


import com.ai.domain.document.Document;
import com.ai.domain.document.TextSegment;
import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.splitter.impl.DocumentSplitters;
import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import com.ai.openai.handler.OpenaiEmbeddingNodeHandler;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 嵌入数据导入器
 */
@Data
@Builder
public class OpenaiEmbeddingStoreIngestor {

    @Builder.Default
    private DocumentSplitter splitter = DocumentSplitters.recursive(500, 0);
    @Builder.Default
    private OpenaiEmbeddingNodeHandler embeddingNodeHandler = new OpenaiEmbeddingNodeHandler();
    @Builder.Default
    private OpenaiEmbeddingMemoryStore<TextSegment> store = new OpenaiEmbeddingMemoryStore<>();

    public void ingest(Document document) {
        this.ingest(Collections.singletonList(document));
    }

    public void ingest(Document... documents) {
        this.ingest(Arrays.asList(documents));
    }

    public void ingest(List<Document> documents) {
        for (Document document : documents) {
            List<TextSegment> segments = splitter.split(document);
            List<String> collect = segments.stream().map(TextSegment::getText).collect(Collectors.toList());
            List<EmbeddingObject> execute = embeddingNodeHandler.execute(collect);
            store.addAll(execute, segments);
        }
    }

}
