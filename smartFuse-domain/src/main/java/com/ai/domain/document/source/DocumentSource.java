package com.ai.domain.document.source;

import com.ai.domain.document.Metadata;

import java.io.IOException;
import java.io.InputStream;


public interface DocumentSource {

    InputStream inputStream() throws IOException;

    Metadata metadata();

}
