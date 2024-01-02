package com.ai.domain.document.splitter;

import com.ai.domain.document.Document;
import com.ai.domain.document.TextSegment;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface DocumentSplitter {


    List<TextSegment> split(Document document);

    default List<TextSegment> splitAll(List<Document> documents) {
        return documents.stream()
                .flatMap(document -> split(document).stream())
                .collect(toList());
    }
}

