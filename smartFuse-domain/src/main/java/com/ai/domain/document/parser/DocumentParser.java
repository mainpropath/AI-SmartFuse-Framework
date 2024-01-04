package com.ai.domain.document.parser;

import com.ai.domain.document.Document;

import java.io.InputStream;

/**
 * 解析器接口
 */
public interface DocumentParser {

    Document parse(InputStream inputStream);

}
