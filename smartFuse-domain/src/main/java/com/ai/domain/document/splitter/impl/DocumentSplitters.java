package com.ai.domain.document.splitter.impl;

import com.ai.domain.document.splitter.DocumentSplitter;
import com.ai.domain.document.tokenizer.Tokenizer;

public class DocumentSplitters {

    public static DocumentSplitter recursive(int maxSegmentSizeInTokens,
                                             int maxOverlapSizeInTokens,
                                             Tokenizer tokenizer) {
        return new DocumentByParagraphSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer,
                new DocumentByLineSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer,
                        new DocumentBySentenceSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer,
                                new DocumentByWordSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenizer)
                        )
                )
        );
    }

    public static DocumentSplitter recursive(int maxSegmentSizeInChars, int maxOverlapSizeInTokens) {
        return recursive(maxSegmentSizeInChars, maxOverlapSizeInTokens, null);
    }
}
