package com.ai.domain.memory.embedding.impl;

import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.memory.embedding.EmbeddingMemoryStore;
import com.ai.domain.memory.embedding.EmbeddingStoreJsonCodec;
import com.ai.domain.memory.embedding.EmbeddingStoreJsonCodecFactory;
import com.ai.domain.spi.ServiceHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ai.common.util.Utils.randomUUID;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * 嵌入数据存储器，存放在内存当中
 */
@lombok.Data
public class SimpleEmbeddingMemoryStore implements EmbeddingMemoryStore<Embedding> {

    private static final EmbeddingStoreJsonCodec CODEC = loadCodec();
    private final Map<String, Embedding> idToEmbeddingData = new ConcurrentHashMap<>();

    private static EmbeddingStoreJsonCodec loadCodec() {
        Collection<EmbeddingStoreJsonCodecFactory> factories = ServiceHelper.loadFactories(
                EmbeddingStoreJsonCodecFactory.class);
        for (EmbeddingStoreJsonCodecFactory factory : factories) {
            return factory.create();
        }
        return new GsonInMemoryEmbeddingStoreJsonCodec();
    }

    public static EmbeddingMemoryStore<Embedding> fromJson(String json) {
        return CODEC.fromJson(json);
    }

    public static EmbeddingMemoryStore<Embedding> fromFile(Path filePath) {
        try {
            String json = new String(Files.readAllBytes(filePath));
            return fromJson(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void serializeToFile(Path filePath) {
        try {
            String json = serializeToJson();
            Files.write(filePath, json.getBytes(), CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String serializeToJson() {
        return CODEC.toJson(this);
    }

}














