package com.ai.domain.document.parser.impl;

import com.ai.domain.document.Document;
import com.ai.domain.document.Metadata;
import com.ai.domain.document.parser.DocumentParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
import java.io.InputStream;

import static com.ai.domain.document.Document.DOCUMENT_TYPE;
import static com.ai.domain.document.DocumentType.PDF;

/**
 * pdf 内容解析器
 */
public class PdfDocumentParser implements DocumentParser {

    @Override
    public Document parse(InputStream inputStream) {
        try {
            PDDocument pdfDocument = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(pdfDocument);
            pdfDocument.close();
            return Document.from(content, Metadata.from(DOCUMENT_TYPE, PDF));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
