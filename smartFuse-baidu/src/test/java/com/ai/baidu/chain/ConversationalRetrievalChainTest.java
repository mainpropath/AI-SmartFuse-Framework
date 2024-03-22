package com.ai.baidu.chain;


import com.ai.baidu.achieve.ApiData;
import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.client.BaiduClient;
import com.ai.baidu.model.BaiduChatModel;
import com.ai.baidu.model.BaiduEmbeddingModel;
import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.chain.impl.ConversationalRetrievalChain;
import com.ai.domain.document.Document;
import com.ai.domain.document.FileSystemDocumentLoader;
import com.ai.domain.memory.chat.impl.SimpleChatHistoryRecorder;
import com.ai.domain.memory.embedding.impl.SimpleEmbeddingStoreIngestor;
import com.ai.domain.memory.embedding.impl.SimpleEmbeddingStoreRetriever;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


public class ConversationalRetrievalChainTest {

    private ConversationalRetrievalChain conversationalRetrievalChain;

    public static Path toPath(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                return Paths.get(file.toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Before
    public void before() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://aip.baidubce.com");
        ApiData apiData = ApiData.builder()
                .apiKey("**************************")
                .secretKey("**************************")
                .appId("**************************")
                .build();
        configuration.setKeyList(Arrays.asList(apiData));
        configuration.setKeyStrategy(new FirstKeyStrategy<ApiData>());
        BaiduClient.SetConfiguration(configuration);
        // 测试文件路径
        String filePath = "D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\ConversationalRetrievalChainTest-中文.txt";
//                "D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\ConversationalRetrievalChainTest-英文.txt";
        // 创建嵌入数据导入器，这里可以设置你指定的存储器，也可以直接使用其中默认的存储器。
        SimpleEmbeddingStoreIngestor ingestor = SimpleEmbeddingStoreIngestor.builder().embeddingModel(new BaiduEmbeddingModel()).build();
        Document document = FileSystemDocumentLoader.loadDocument(toPath(filePath));
        // 将数据导入到存储器当中
        ingestor.ingest(document);
        System.out.println(document.metadata());
        System.out.println(document.text());
        // 获取存储器，并设置其对应的检索器，向检索器当中设置检索器检索的嵌入存储器。
        this.conversationalRetrievalChain = ConversationalRetrievalChain.builder()
                .chatModel(new BaiduChatModel())
                .embeddingModel(new BaiduEmbeddingModel())
                .historyRecorder(SimpleChatHistoryRecorder.builder().build())
                .retriever(SimpleEmbeddingStoreRetriever.builder().embeddingMemoryStore(ingestor.getStore()).build())
                .build();
    }

    @Test
    public void test_1() {
        System.err.println("end");
    }

    @Test
    public void test_embedding_data_retriever_with_en() {
        String question = "What kind of person is Little Red Riding Hood?";
        String res = conversationalRetrievalChain.run(question);
        System.out.println(res);
    }

    @Test
    public void test_embedding_data_retriever_with_ch() {
        String question = "小红帽要去干什么？";
        String res = conversationalRetrievalChain.run(question);
        System.out.println(res);
    }


}
