package com.ai.openai.memory.embedding;

import com.ai.common.strategy.impl.FirstKeyStrategy;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.document.Document;
import com.ai.domain.document.FileSystemDocumentLoader;
import com.ai.domain.memory.embedding.EmbeddingMemoryStore;
import com.ai.openai.achieve.Configuration;
import com.ai.openai.client.OpenAiClient;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 嵌入测试
 */
public class EmbeddingMemoryTest {

    private OpenaiEmbeddingMemoryStore embeddingMemoryStore;

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
    public void test_ingest() {
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
        for (String file : filePath) {
            // 导入数据并放入List当中
            documents.add(FileSystemDocumentLoader.loadDocument(toPath(file)));
        }
        // 将数据导入到存储器当中
        ingestor.ingest(documents);
        // 获取存储器
        this.embeddingMemoryStore = (OpenaiEmbeddingMemoryStore) ingestor.getStore();
        // 获取存储器当中的数据
        List<Embedding> allData = embeddingMemoryStore.getAllData();
//        for (Embedding embedding : allData) {
//            System.out.println(embedding);
//        }
    }

    @Test
    public void test_serialize_to_file() {
        String filePath = "D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\EmbeddingMemoryStoreTest.txt";
        this.embeddingMemoryStore.serializeToFile(toPath(filePath));
    }

    @Test
    public void test_load_from_file() {
        String filePath = "D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\EmbeddingMemoryStoreTest.txt";
        EmbeddingMemoryStore<Embedding> embeddingEmbeddingMemoryStore = OpenaiEmbeddingMemoryStore.fromFile(toPath(filePath));
        System.out.println(embeddingEmbeddingMemoryStore.getAllData());
    }

}
