package com.ai.openai.memory.embedding;

import com.ai.domain.document.Document;
import com.ai.domain.document.FileSystemDocumentLoader;
import com.ai.domain.document.TextSegment;
import org.junit.Before;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * 嵌入测试
 */
public class EmbeddingMemoryTest {

    @Before
    public void test_ingest() {
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
        OpenaiEmbeddingMemoryStore<TextSegment> store = ingestor.getStore();
        // 获取存储器当中的数据
        List<OpenaiEmbeddingMemoryStore.Entry<TextSegment>> allData = store.getAllData();
        for (OpenaiEmbeddingMemoryStore.Entry<TextSegment> msg : allData) {
            System.out.println(msg.getId() + " " + msg.getMetadata().getText() + " " + msg.getEmbedding());
        }
    }

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


}
