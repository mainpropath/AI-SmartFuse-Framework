package com.ai.openai.memory.embedding;

import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.ai.common.util.Utils.randomUUID;

/**
 * 嵌入数据存储器，存放在内存当中
 */
@lombok.Data
public class OpenaiEmbeddingMemoryStore<Data> {

    private final Map<String, Entry<Data>> idToEmbeddingData = new ConcurrentHashMap<>();

    public String add(EmbeddingObject embeddingObject) {
        String id = randomUUID();
        add(id, embeddingObject);
        return id;
    }

    public void add(String id, EmbeddingObject embeddingObject) {
        add(id, embeddingObject, null);
    }

    public String add(EmbeddingObject embeddingObject, Data data) {
        String id = randomUUID();
        add(id, embeddingObject, data);
        return id;
    }

    public void add(String id, EmbeddingObject embeddingObject, Data data) {
        idToEmbeddingData.put(id, new Entry(id, embeddingObject, data));
    }

    public List<String> addAll(List<EmbeddingObject> embeddingObjects) {
        ArrayList<String> ids = new ArrayList<>();
        for (EmbeddingObject embeddingObject : embeddingObjects) {
            ids.add(add(embeddingObject));
        }
        return ids;
    }

    public List<String> addAll(List<EmbeddingObject> embeddings, List<Data> embedded) {
        if (embeddings.size() != embedded.size()) {
            throw new IllegalArgumentException("The list of embeddings and embedded must have the same size");
        }

        List<String> ids = new ArrayList<>();
        for (int i = 0; i < embeddings.size(); i++) {
            ids.add(add(embeddings.get(i), embedded.get(i)));
        }
        return ids;
    }

    public Entry<Data> getData(String id) {
        return idToEmbeddingData.get(id);
    }

    public List<Entry<Data>> getAllData() {
        return idToEmbeddingData.values().stream().collect(Collectors.toList());
    }

    @lombok.Data
    @AllArgsConstructor
    public static class Entry<Metadata> {
        String id;
        EmbeddingObject embedding;
        Metadata metadata;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry that = (Entry) o;
            return Objects.equals(this.id, that.id)
                    && Objects.equals(this.embedding, that.embedding)
                    && Objects.equals(this.metadata, that.metadata);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, embedding, metadata);
        }
    }

}














