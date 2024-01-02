package com.ai.domain.document.splitter.impl;

import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;


public class DocumentByRegexSplitter extends HierarchicalDocumentSplitter {

    private final String regex;
    private final String joinDelimiter;

    public DocumentByRegexSplitter(String regex,
                                   String joinDelimiter,
                                   int maxSegmentSizeInChars,
                                   int maxOverlapSizeInChars) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, null);
        this.regex = regex;
        this.joinDelimiter = joinDelimiter;
    }

    public DocumentByRegexSplitter(String regex,
                                   String joinDelimiter,
                                   int maxSegmentSizeInChars,
                                   int maxOverlapSizeInChars,
                                   DocumentSplitter subSplitter) {
        super(maxSegmentSizeInChars, maxOverlapSizeInChars, null, subSplitter);
        this.regex = regex;
        this.joinDelimiter = joinDelimiter;
    }

    public DocumentByRegexSplitter(String regex,
                                   String joinDelimiter,
                                   int maxSegmentSizeInTokens,
                                   int maxOverlapSizeInTokens,
                                   Tokenizer tokenizer) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, null);
        this.regex = regex;
        this.joinDelimiter = joinDelimiter;
    }

    public DocumentByRegexSplitter(String regex,
                                   String joinDelimiter,
                                   int maxSegmentSizeInTokens,
                                   int maxOverlapSizeInTokens,
                                   Tokenizer tokenizer,
                                   DocumentSplitter subSplitter) {
        super(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer, subSplitter);
        this.regex = regex;
        this.joinDelimiter = joinDelimiter;
    }

    @Override
    public String[] split(String text) {
        return text.split(regex);
    }

    @Override
    public String joinDelimiter() {
        return joinDelimiter;
    }

    @Override
    protected DocumentSplitter defaultSubSplitter() {
        return null;
    }
}
