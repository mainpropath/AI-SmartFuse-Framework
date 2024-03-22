package com.ai.domain.memory.embedding.impl;


import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.document.Document;
import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.splitter.impl.DocumentSplitters;
import com.ai.domain.memory.embedding.EmbeddingMemoryStore;
import com.ai.domain.memory.embedding.EmbeddingStoreIngestor;
import com.ai.domain.model.EmbeddingModel;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 嵌入数据导入器
 */
@Data
@Builder
public class SimpleEmbeddingStoreIngestor implements EmbeddingStoreIngestor {

    @Builder.Default
    private DocumentSplitter splitter = DocumentSplitters.recursive(100, 0);
    @NotNull
    private EmbeddingModel embeddingModel;
    @Builder.Default
    private EmbeddingMemoryStore<Embedding> store = new SimpleEmbeddingMemoryStore();

    public void ingest(Document document) {
        this.ingest(Collections.singletonList(document));
    }

    public void ingest(Document... documents) {
        this.ingest(Arrays.asList(documents));
    }

    public void ingest(List<Document> documents) {
        for (Document document : documents) {
            List<Embedding> data = embeddingModel.embedAll(splitter.split(document)).getData();
            store.addAll(data);
        }
    }

}
