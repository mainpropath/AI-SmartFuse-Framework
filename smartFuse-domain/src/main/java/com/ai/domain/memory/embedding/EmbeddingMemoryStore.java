package com.ai.domain.memory.embedding;

import java.util.List;

/**
 * 嵌入数据存储器，存放在内存当中
 */
public interface EmbeddingMemoryStore<Data> {

    String add(Data embedding);

    void add(String id, Data embedding);

    List<String> addAll(List<Data> embedding);

    List<Data> getAllData();

}














