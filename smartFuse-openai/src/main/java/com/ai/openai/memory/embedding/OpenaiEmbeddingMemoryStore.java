package com.ai.openai.memory.embedding;

import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.ai.common.util.Utils.randomUUID;

@Data
public class OpenaiEmbeddingMemoryStore<Metadata> {

    private final Map<String, Entry<Metadata>> idToEmbeddingData = new ConcurrentHashMap<>();

    public String add(EmbeddingObject embeddingObject) {
        String id = randomUUID();
        add(id, embeddingObject);
        return id;
    }

    public void add(String id, EmbeddingObject embeddingObject) {
        add(id, embeddingObject, null);
    }

    public String add(EmbeddingObject embeddingObject, Metadata metadata) {
        String id = randomUUID();
        add(id, embeddingObject, metadata);
        return id;
    }

    public void add(String id, EmbeddingObject embeddingObject, Metadata metadata) {
        idToEmbeddingData.put(id, new Entry(id, embeddingObject, metadata));
    }

    public List<String> addAll(List<EmbeddingObject> embeddingObjects) {
        ArrayList<String> ids = new ArrayList<>();
        for (EmbeddingObject embeddingObject : embeddingObjects) {
            ids.add(add(embeddingObject));
        }
        return ids;
    }

    public List<String> addAll(List<EmbeddingObject> embeddings, List<Metadata> embedded) {
        if (embeddings.size() != embedded.size()) {
            throw new IllegalArgumentException("The list of embeddings and embedded must have the same size");
        }

        List<String> ids = new ArrayList<>();
        for (int i = 0; i < embeddings.size(); i++) {
            ids.add(add(embeddings.get(i), embedded.get(i)));
        }
        return ids;
    }

    @Data
    @AllArgsConstructor
    protected static class Entry<Metadata> {
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














