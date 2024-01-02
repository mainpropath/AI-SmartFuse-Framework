package com.ai.domain.document.parser;

import com.ai.domain.document.Document;

import java.io.InputStream;


public interface DocumentParser {

    Document parse(InputStream inputStream);

}
