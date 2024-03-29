package com.ai.domain.memory.embedding;


import com.ai.domain.document.Document;

import java.util.List;

/**
 * 嵌入数据导入器
 */
public interface EmbeddingStoreIngestor {

    /**
     * 解析文档信息
     */
    void ingest(Document document);

    void ingest(Document... documents);

    void ingest(List<Document> documents);

}
