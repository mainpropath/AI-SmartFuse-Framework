package com.ai.openai.chain;


import com.ai.domain.document.Document;
import com.ai.domain.document.FileSystemDocumentLoader;
import com.ai.domain.document.TextSegment;
import com.ai.openai.memory.embedding.OpenaiEmbeddingStoreIngestor;
import com.ai.openai.memory.embedding.OpenaiEmbeddingStoreRetriever;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ai.openai.memory.embedding.EmbeddingMemoryTest.toPath;

public class iConversationalRetrievalChainTest {

    private OpenaiConversationalRetrievalChain conversationalRetrievalChain;

    @Before
    public void before() {
        // 测试文件路径
        String[] filePath = {"D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\ConversationalRetrievalChainTest-中文.txt",
                "D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\ConversationalRetrievalChainTest-英文.txt"};
        // 创建嵌入数据导入器，这里可以设置你指定的存储器，也可以直接使用其中默认的存储器。
        OpenaiEmbeddingStoreIngestor ingestor = OpenaiEmbeddingStoreIngestor.builder().build();
        List<Document> documents = new ArrayList<>();
        // 导入数据并放入List当中
        for (String file : filePath) {
            documents.add(FileSystemDocumentLoader.loadDocument(toPath(file)));
        }
        // 将数据导入到存储器当中
        ingestor.ingest(documents);
        // 获取存储器，并设置其对应的检索器，向检索器当中设置检索器检索的嵌入存储器。
        this.conversationalRetrievalChain = OpenaiConversationalRetrievalChain.builder()
                .retriever(OpenaiEmbeddingStoreRetriever.<TextSegment>builder().embeddingMemoryStore(ingestor.getStore()).build())
                .build();
    }

    @Test
    public void test_embedding_data_retriever_with_en() {
        String question = "What kind of person is Little Red Riding Hood?";
        String res = conversationalRetrievalChain.run(question);
        System.out.println(res);
    }

    @Test
    public void test_embedding_data_retriever_with_ch() {
        String question = "小红帽是一个怎样的人？";
        String res = conversationalRetrievalChain.run(question);
        System.out.println(res);
    }


}
