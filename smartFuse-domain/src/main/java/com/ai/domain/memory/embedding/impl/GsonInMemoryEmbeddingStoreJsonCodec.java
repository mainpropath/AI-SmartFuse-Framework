package com.ai.domain.memory.embedding.impl;

import com.ai.domain.memory.embedding.EmbeddingMemoryStore;
import com.ai.domain.memory.embedding.EmbeddingStoreJsonCodec;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class GsonInMemoryEmbeddingStoreJsonCodec implements EmbeddingStoreJsonCodec {

    @Override
    public SimpleEmbeddingMemoryStore fromJson(String json) {
        Type type = new TypeToken<SimpleEmbeddingMemoryStore>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    @Override
    public String toJson(EmbeddingMemoryStore<?> store) {
        return new Gson().toJson(store);
    }
}
