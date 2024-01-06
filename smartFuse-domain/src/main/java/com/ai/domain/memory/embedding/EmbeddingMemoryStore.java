package com.ai.domain.memory.embedding;

import java.util.List;

/**
 * 嵌入数据存储器，存放在内存当中
 */
public interface EmbeddingMemoryStore<Data> {

    public String add(Data embedding);

    public void add(String id, Data embedding);

    public List<String> addAll(List<Data> embedding);

    public List<Data> getAllData();

}














