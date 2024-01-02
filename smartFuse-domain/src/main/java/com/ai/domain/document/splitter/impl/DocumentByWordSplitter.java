package com.ai.domain.document.splitter.impl;

import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;

public class DocumentByWordSplitter extends HierarchicalDocumentSplitter {

    public DocumentByWordSplitter(int maxSegmentSizeInChars,
                                  int maxOverlapSizeInChars) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, null);
    }

    public DocumentByWordSplitter(int maxSegmentSizeInChars,
                                  int maxOverlapSizeInChars,
                                  DocumentSplitter subSplitter) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, subSplitter);
    }

    public DocumentByWordSplitter(int maxSegmentSizeInTokens,
                                  int maxOverlapSizeInTokens,
                                  Tokenizer tokenizer) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, null);
    }

    public DocumentByWordSplitter(int maxSegmentSizeInTokens,
                                  int maxOverlapSizeInTokens,
                                  Tokenizer tokenizer,
                                  DocumentSplitter subSplitter) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, subSplitter);
    }

    @Override
    public String[] split(String text) {
        return text.split("\\s+"); // additional whitespaces are ignored
    }

    @Override
    public String joinDelimiter() {
        return " ";
    }

    @Override
    protected DocumentSplitter defaultSubSplitter() {
        return new DocumentByCharacterSplitter(maxSegmentSize, maxOverlapSize, tokenizer);
    }
}
