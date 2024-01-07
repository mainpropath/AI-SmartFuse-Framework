package com.ai.openai.memory.embedding;

import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.memory.embedding.EmbeddingMemoryStore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class GsonInMemoryEmbeddingStoreJsonCodec implements EmbeddingStoreJsonCodec {

    @Override
    public OpenaiEmbeddingMemoryStore fromJson(String json) {
        Type type = new TypeToken<OpenaiEmbeddingMemoryStore>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    @Override
    public String toJson(EmbeddingMemoryStore<?> store) {
        return new Gson().toJson(store);
    }
}
