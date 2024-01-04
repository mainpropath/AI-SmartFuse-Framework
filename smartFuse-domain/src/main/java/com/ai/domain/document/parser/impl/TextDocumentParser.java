package com.ai.domain.document.parser.impl;

import com.ai.domain.document.Document;
import com.ai.domain.document.DocumentType;
import com.ai.domain.document.Metadata;
import com.ai.domain.document.parser.DocumentParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.ai.domain.document.Document.DOCUMENT_TYPE;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 文本文件解析器
 */
public class TextDocumentParser implements DocumentParser {

    private final DocumentType documentType;
    private final Charset charset;

    public TextDocumentParser(DocumentType documentType) {
        this(documentType, UTF_8);
    }

    public TextDocumentParser(DocumentType documentType, Charset charset) {
        this.documentType = documentType;
        this.charset = charset;
    }

    @Override
    public Document parse(InputStream inputStream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            String text = new String(buffer.toByteArray(), charset);

            return Document.from(text, Metadata.from(DOCUMENT_TYPE, documentType.toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
