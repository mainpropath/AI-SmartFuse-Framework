package com.ai.domain.document.parser.impl;

import com.ai.domain.document.Document;
import com.ai.domain.document.DocumentType;
import com.ai.domain.document.Metadata;
import com.ai.domain.document.parser.DocumentParser;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POITextExtractor;

import java.io.IOException;
import java.io.InputStream;

import static com.ai.domain.document.Document.DOCUMENT_TYPE;

public class MsOfficeDocumentParser implements DocumentParser {

    private final DocumentType documentType;

    public MsOfficeDocumentParser(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public Document parse(InputStream inputStream) {
        try (POITextExtractor extractor = ExtractorFactory.createExtractor(inputStream)) {
            return new Document(extractor.getText(), Metadata.from(DOCUMENT_TYPE, documentType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
