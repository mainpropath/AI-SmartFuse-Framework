package com.ai.domain.document;


import com.ai.domain.document.source.impl.UrlSource;

import java.net.MalformedURLException;
import java.net.URL;

import static com.ai.domain.document.DocumentLoaderUtils.parserFor;

public class UrlDocumentLoader {

    public static Document load(URL url) {
        return load(url, DocumentType.of(url.toString()));
    }

    public static Document load(String url) {
        try {
            return load(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document load(URL url, DocumentType documentType) {
        return DocumentLoaderUtils.load(UrlSource.from(url), parserFor(documentType));
    }

    public static Document load(String url, DocumentType documentType) {
        try {
            return load(new URL(url), documentType);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
