package com.ai.openai.chain;


import com.ai.common.strategy.impl.FirstKeyStrategy;
import com.ai.domain.chain.impl.ConversationalRetrievalChain;
import com.ai.domain.document.Document;
import com.ai.domain.document.FileSystemDocumentLoader;
import com.ai.openai.achieve.Configuration;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.memory.chat.OpenaiChatHistoryRecorder;
import com.ai.openai.memory.embedding.OpenaiEmbeddingStoreIngestor;
import com.ai.openai.memory.embedding.OpenaiEmbeddingStoreRetriever;
import com.ai.openai.model.OpenaiChatModel;
import com.ai.openai.model.OpenaiEmbeddingModel;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ai.openai.memory.embedding.EmbeddingMemoryTest.toPath;

public class ConversationalRetrievalChainTest {

    private ConversationalRetrievalChain conversationalRetrievalChain;

    @Before
    public void before() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com");
        configuration.setKeyList(Arrays.asList("************************"));
        configuration.setKeyStrategy(new FirstKeyStrategy<String>());
        configuration.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        OpenAiClient.SetConfiguration(configuration);
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
        this.conversationalRetrievalChain = ConversationalRetrievalChain.builder()
                .chatModel(new OpenaiChatModel())
                .embeddingModel(new OpenaiEmbeddingModel())
                .historyRecorder(OpenaiChatHistoryRecorder.builder().build())
                .retriever(OpenaiEmbeddingStoreRetriever.builder().embeddingMemoryStore(ingestor.getStore()).build())
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
        String question = "小红帽对奶奶说了什么？";
        String res = conversationalRetrievalChain.run(question);
        System.out.println(res);
    }


}
