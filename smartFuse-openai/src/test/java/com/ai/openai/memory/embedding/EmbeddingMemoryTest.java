package com.ai.openai.memory.embedding;

import com.ai.domain.document.Document;
import com.ai.domain.document.FileSystemDocumentLoader;
import com.ai.domain.document.TextSegment;
import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.splitter.impl.DocumentSplitters;
import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import com.ai.openai.chain.OpenaiConversationalRetrievalChain;
import com.ai.openai.handler.OpenaiEmbeddingNodeHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class EmbeddingMemoryTest {


    private OpenaiConversationalRetrievalChain openaiConversationalRetrievalChain;

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
    public void test_load() {
        // 测试文件路径
        String[] filePath = {"D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\ConversationalRetrievalChainTest-中文.txt",
                "D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\ConversationalRetrievalChainTest-英文.txt"};
        // 创建文档分束器
        DocumentSplitter splitter = DocumentSplitters.recursive(500, 0);
        // 创建嵌入节点
        OpenaiEmbeddingNodeHandler openaiEmbeddingNodeHandler = new OpenaiEmbeddingNodeHandler();
        // 创建嵌入数据存储器
        OpenaiEmbeddingMemoryStore<TextSegment> store = new OpenaiEmbeddingMemoryStore<>();
        for (int i = 0; i < filePath.length; i++) {
            Path path = toPath(filePath[i]);
            Document document = FileSystemDocumentLoader.loadDocument(path);
            List<TextSegment> segments = splitter.split(document);
            // 开始对文档进行嵌入操作
            List<String> collect = segments.stream().map(TextSegment::getText).collect(Collectors.toList());
            List<EmbeddingObject> execute = openaiEmbeddingNodeHandler.execute(collect);
            // 添加嵌入的数据到存储器当中
            store.addAll(execute, segments);
        }
        // 创建嵌入数据索引器，设置对应的存储器
        OpenaiEmbeddingStoreRetriever<TextSegment> retriever = OpenaiEmbeddingStoreRetriever.<TextSegment>builder()
                .openaiEmbeddingMemoryStore(store).build();
        // 创建 openaiConversationalRetrievalChain
        this.openaiConversationalRetrievalChain = OpenaiConversationalRetrievalChain.builder().retriever(retriever).build();
    }

    @Test
    public void test_embedding_data_retriever_with_en() {
        String question = "What kind of person is Little Red Riding Hood?";
        String res = openaiConversationalRetrievalChain.run(question);
        log.info("模型回复：{}", res);
    }

    @Test
    public void test_embedding_data_retriever_with_ch() {
        String question = "小红帽是一个怎样的人？";
        String res = openaiConversationalRetrievalChain.run(question);
        log.info("模型回复：{}", res);
    }


}
