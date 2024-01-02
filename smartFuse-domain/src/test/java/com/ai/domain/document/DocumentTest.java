package com.ai.domain.document;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description: 文档加载测试
 **/
@Slf4j
public class DocumentTest {

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

    @Test
    public void test_load_txt() {
        Path filePath = toPath("D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\中文测试.txt");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        System.out.println(document);
    }

    @Test
    public void test_load_word() {
        Path filePath = toPath("D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\中文测试.docx");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        System.out.println(document);
    }

    @Test
    public void test_load_pdf() {
        Path filePath = toPath("D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\中文测试.pdf");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        System.out.println(document);
    }

}
