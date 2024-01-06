package com.ai.openai.memory.embedding;

import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.memory.embedding.EmbeddingMemoryStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ai.common.util.Utils.randomUUID;

/**
 * 嵌入数据存储器，存放在内存当中
 */
@lombok.Data
public class OpenaiEmbeddingMemoryStore implements EmbeddingMemoryStore<Embedding> {

    private final Map<String, Embedding> idToEmbeddingData = new ConcurrentHashMap<>();

    @Override
    public String add(Embedding embedding) {
        String id = randomUUID();
        add(id, embedding);
        return id;
    }

    @Override
    public void add(String id, Embedding embedding) {
        idToEmbeddingData.put(id, embedding);
    }

    @Override
    public List<String> addAll(List<Embedding> embeddings) {
        List<String> ids = new ArrayList<>();
        for (Embedding embedding : embeddings) {
            ids.add(add(embedding));
        }
        return ids;
    }

    @Override
    public List<Embedding> getAllData() {
        return idToEmbeddingData.values().stream().collect(Collectors.toList());
    }

}














