package com.ai.domain.document;

import com.ai.domain.document.source.impl.S3Source;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.domain.document.DocumentLoaderUtils.parserFor;


public class S3FileLoader extends AbstractS3Loader<Document> {
    private final String key;

    private S3FileLoader(Builder builder) {
        super(builder);
        this.key = ensureNotBlank(builder.key, "key");
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected Document load(S3Client s3Client) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder().bucket(bucket).key(key).build();
            ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(objectRequest);
            return DocumentLoaderUtils.load(new S3Source(bucket, key, inputStream), parserFor(DocumentType.of(key)));
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to load document from s3", e);
        }
    }

    public static final class Builder extends AbstractS3Loader.Builder<Builder> {

        private String key;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        @Override
        public S3FileLoader build() {
            return new S3FileLoader(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
