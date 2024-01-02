package com.ai.domain.document.splitter.impl;

import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;


public class DocumentByLineSplitter extends HierarchicalDocumentSplitter {

    public DocumentByLineSplitter(int maxSegmentSizeInChars,
                                  int maxOverlapSizeInChars) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, null);
    }

    public DocumentByLineSplitter(int maxSegmentSizeInChars,
                                  int maxOverlapSizeInChars,
                                  DocumentSplitter subSplitter) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, subSplitter);
    }

    public DocumentByLineSplitter(int maxSegmentSizeInTokens,
                                  int maxOverlapSizeInTokens,
                                  Tokenizer tokenizer) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, null);
    }

    public DocumentByLineSplitter(int maxSegmentSizeInTokens,
                                  int maxOverlapSizeInTokens,
                                  Tokenizer tokenizer,
                                  DocumentSplitter subSplitter) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, subSplitter);
    }

    @Override
    public String[] split(String text) {
        return text.split("\\s*\\R\\s*"); // additional whitespaces are ignored
    }

    @Override
    public String joinDelimiter() {
        return "\n";
    }

    @Override
    protected DocumentSplitter defaultSubSplitter() {
        return new DocumentBySentenceSplitter(maxSegmentSize, maxOverlapSize, tokenizer);
    }
}
