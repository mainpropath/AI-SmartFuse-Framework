package com.ai.domain.document.splitter.impl;


import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;


public class DocumentByCharacterSplitter extends HierarchicalDocumentSplitter {

    public DocumentByCharacterSplitter(int maxSegmentSizeInChars,
                                       int maxOverlapSizeInChars) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, null);
    }

    public DocumentByCharacterSplitter(int maxSegmentSizeInChars,
                                       int maxOverlapSizeInChars,
                                       DocumentSplitter subSplitter) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, subSplitter);
    }

    public DocumentByCharacterSplitter(int maxSegmentSizeInTokens,
                                       int maxOverlapSizeInTokens,
                                       Tokenizer tokenizer) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, null);
    }

    public DocumentByCharacterSplitter(int maxSegmentSizeInTokens,
                                       int maxOverlapSizeInTokens,
                                       Tokenizer tokenizer,
                                       DocumentSplitter subSplitter) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, subSplitter);
    }

    @Override
    public String[] split(String text) {
        return text.split("");
    }

    @Override
    public String joinDelimiter() {
        return "";
    }

    @Override
    protected DocumentSplitter defaultSubSplitter() {
        return null;
    }
}
