package com.ai.domain.document.splitter.impl;

import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;


public class DocumentBySentenceSplitter extends HierarchicalDocumentSplitter {

    public DocumentBySentenceSplitter(int maxSegmentSizeInChars,
                                      int maxOverlapSizeInChars) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, null);
    }

    public DocumentBySentenceSplitter(int maxSegmentSizeInChars,
                                      int maxOverlapSizeInChars,
                                      DocumentSplitter subSplitter) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, subSplitter);
    }

    public DocumentBySentenceSplitter(int maxSegmentSizeInTokens,
                                      int maxOverlapSizeInTokens,
                                      Tokenizer tokenizer) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, null);
    }

    public DocumentBySentenceSplitter(int maxSegmentSizeInTokens,
                                      int maxOverlapSizeInTokens,
                                      Tokenizer tokenizer,
                                      DocumentSplitter subSplitter) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, subSplitter);
    }

    @Override
    public String[] split(String text) {
        return text.split("\\s*[.。！？!?]\\s*");
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
