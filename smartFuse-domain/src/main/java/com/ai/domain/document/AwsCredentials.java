package com.ai.domain.document;

import static com.ai.common.util.Utils.areNotNullOrBlank;
import static com.ai.common.util.ValidationUtils.ensureNotBlank;


public class AwsCredentials {

    private final String accessKeyId;
    private final String secretAccessKey;
    private String sessionToken;

    public AwsCredentials(String accessKeyId, String secretAccessKey, String sessionToken) {
        this.accessKeyId = ensureNotBlank(accessKeyId, "accessKeyId");
        this.secretAccessKey = ensureNotBlank(secretAccessKey, "secretAccessKey");
        this.sessionToken = sessionToken;
    }

    public AwsCredentials(String accessKeyId, String secretAccessKey) {
        this(accessKeyId, secretAccessKey, null);
    }


    public String accessKeyId() {
        return accessKeyId;
    }

    public String secretAccessKey() {
        return secretAccessKey;
    }

    public String sessionToken() {
        return sessionToken;
    }

    public boolean hasAccessKeyIdAndSecretKey() {
        return areNotNullOrBlank(accessKeyId, secretAccessKey);
    }

    public boolean hasAllCredentials() {
        return areNotNullOrBlank(accessKeyId, secretAccessKey, sessionToken);
    }
}
