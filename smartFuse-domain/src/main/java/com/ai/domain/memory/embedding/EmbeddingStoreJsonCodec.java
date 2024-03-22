package com.ai.domain.memory.embedding;


import com.ai.domain.memory.embedding.impl.SimpleEmbeddingMemoryStore;

public interface EmbeddingStoreJsonCodec {

    SimpleEmbeddingMemoryStore fromJson(String json);

    String toJson(EmbeddingMemoryStore<?> store);

}
