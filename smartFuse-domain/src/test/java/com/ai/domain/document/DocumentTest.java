package com.ai.domain.document;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文档加载测试
 **/
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

    @Test
    public void test_load_excel() {
        Path filePath = toPath("D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\中文测试.xlsx");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        System.out.println(document);
    }

    @Test
    public void test_load_ppt() {
        Path filePath = toPath("D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\中文测试.pptx");
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        System.out.println(document);
    }

}
