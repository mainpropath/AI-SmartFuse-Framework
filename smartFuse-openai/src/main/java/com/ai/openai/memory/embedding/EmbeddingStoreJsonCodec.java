package com.ai.openai.memory.embedding;


import com.ai.domain.memory.embedding.EmbeddingMemoryStore;

public interface EmbeddingStoreJsonCodec {

    OpenaiEmbeddingMemoryStore fromJson(String json);

    String toJson(EmbeddingMemoryStore<?> store);

}
