package com.ai.domain.document.source.impl;

import com.ai.domain.document.Metadata;
import com.ai.domain.document.source.DocumentSource;

import java.io.IOException;
import java.io.InputStream;

public class S3Source implements DocumentSource {

    private static final String SOURCE = "source";
    private final String bucket;
    private final String key;
    private InputStream inputStream;

    public S3Source(String bucket, String key, InputStream inputStream) {
        this.inputStream = inputStream;
        this.bucket = bucket;
        this.key = key;
    }

    @Override
    public InputStream inputStream() throws IOException {
        return inputStream;
    }

    @Override
    public Metadata metadata() {
        return new Metadata()
                .add(SOURCE, String.format("s3://%s/%s", bucket, key));
    }
}
