package com.ai.domain.document.splitter.impl;

import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.InputStream;

public class DocumentBySentenceSplitter extends HierarchicalDocumentSplitter {

    private final SentenceDetectorME sentenceDetector;

    public DocumentBySentenceSplitter(int maxSegmentSizeInChars,
                                      int maxOverlapSizeInChars) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, null);
        this.sentenceDetector = createSentenceDetector();
    }

    public DocumentBySentenceSplitter(int maxSegmentSizeInChars,
                                      int maxOverlapSizeInChars,
                                      DocumentSplitter subSplitter) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, subSplitter);
        this.sentenceDetector = createSentenceDetector();
    }

    public DocumentBySentenceSplitter(int maxSegmentSizeInTokens,
                                      int maxOverlapSizeInTokens,
                                      Tokenizer tokenizer) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, null);
        this.sentenceDetector = createSentenceDetector();
    }

    public DocumentBySentenceSplitter(int maxSegmentSizeInTokens,
                                      int maxOverlapSizeInTokens,
                                      Tokenizer tokenizer,
                                      DocumentSplitter subSplitter) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, subSplitter);
        this.sentenceDetector = createSentenceDetector();
    }

    private SentenceDetectorME createSentenceDetector() {
        String sentenceModelFilePath = "/opennlp/opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin";
        try (InputStream is = getClass().getResourceAsStream(sentenceModelFilePath)) {
            return new SentenceDetectorME(new SentenceModel(is));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] split(String text) {
        return sentenceDetector.sentDetect(text);
    }

    @Override
    public String joinDelimiter() {
        return " ";
    }

    @Override
    protected DocumentSplitter defaultSubSplitter() {
        return new DocumentByWordSplitter(maxSegmentSize, maxOverlapSize, tokenizer);
    }
}
