package com.ai.domain.document;

import com.ai.domain.document.parser.DocumentParser;
import com.ai.domain.document.parser.impl.MsOfficeDocumentParser;
import com.ai.domain.document.parser.impl.PdfDocumentParser;
import com.ai.domain.document.parser.impl.TextDocumentParser;
import com.ai.domain.document.source.DocumentSource;

import java.io.InputStream;

class DocumentLoaderUtils {

    static Document load(DocumentSource source, DocumentParser parser) {
        try (InputStream inputStream = source.inputStream()) {
            Document document = parser.parse(inputStream);
            source.metadata().asMap().forEach((key, value) -> document.metadata().add(key, value));
            return document;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load document", e);
        }
    }

    static DocumentParser parserFor(DocumentType type) {
        switch (type) {
            case TXT:
            case HTML:
            case UNKNOWN:
                return new TextDocumentParser(type);
            case PDF:
                return new PdfDocumentParser();
            case DOC:
            case XLS:
            case PPT:
                return new MsOfficeDocumentParser(type);
            default:
                throw new RuntimeException(String.format("Cannot find parser for document type '%s'", type));
        }
    }
}
