package com.ai.domain.document;

import com.ai.domain.document.splitter.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;

import static com.ai.domain.document.DocumentTest.toPath;


@Slf4j
public class SplitterTest {

    private Document document;

    public static void showText(List<TextSegment> textSegments) {
        for (TextSegment text : textSegments) {
            log.info("测试结果：{}", text);
        }
    }

    public static void showText(String[] textSegments) {
        for (String text : textSegments) {
            log.info("测试结果：{}", text);
        }
    }

    @Before
    public void test_load() {
        Path filePath = toPath("D:\\chatGPT-api\\AI-SmartFuse-Framework\\doc\\test\\document\\中文测试.txt");
        this.document = FileSystemDocumentLoader.loadDocument(filePath);
    }

    @Test
    public void test_character_splitter() {
        DocumentByCharacterSplitter documentByCharacterSplitter = new DocumentByCharacterSplitter(10, 1);
        String[] split = documentByCharacterSplitter.split(document.text());
        showText(split);
    }

    @Test
    public void test_line_splitter() {
        DocumentByLineSplitter documentByLineSplitter = new DocumentByLineSplitter(10, 1);
        String[] split = documentByLineSplitter.split(document.text());
        showText(split);
    }

    @Test
    public void test_paragraph_splitter() {
        DocumentByParagraphSplitter documentByParagraphSplitter = new DocumentByParagraphSplitter(10, 0);
        String[] split = documentByParagraphSplitter.split(document.text());
        showText(split);
    }

    @Test
    public void test_sentence_splitter() {
        DocumentBySentenceSplitter documentBySentenceSplitter = new DocumentBySentenceSplitter(10, 0);
        String[] split = documentBySentenceSplitter.split(document.text());
        showText(split);
    }

    @Test
    public void test_word_splitter() {
        DocumentByWordSplitter documentByWordSplitter = new DocumentByWordSplitter(10, 0);
        String[] split = documentByWordSplitter.split(document.text());
        showText(split);
    }


}
