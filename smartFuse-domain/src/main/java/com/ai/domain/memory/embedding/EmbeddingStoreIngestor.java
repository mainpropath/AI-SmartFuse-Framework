package com.ai.domain.memory.embedding;


import com.ai.domain.document.Document;

import java.util.List;

/**
 * 嵌入数据导入器
 */
public interface EmbeddingStoreIngestor {

    public void ingest(Document document);

    public void ingest(Document... documents);

    public void ingest(List<Document> documents);

}
