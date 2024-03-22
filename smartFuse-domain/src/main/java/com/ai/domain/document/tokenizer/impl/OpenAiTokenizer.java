package com.ai.domain.document.tokenizer.impl;

import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.document.tokenizer.Tokenizer;
import com.ai.domain.tools.ToolExecutionRequest;
import com.ai.domain.tools.ToolParameters;
import com.ai.domain.tools.ToolSpecification;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.ai.common.util.Exceptions.illegalArgument;
import static com.ai.common.util.ValidationUtils.ensureNotBlank;


public class OpenAiTokenizer implements Tokenizer {

    private final String modelName;
    private final Optional<Encoding> encoding;

    public OpenAiTokenizer(String modelName) {
        this.modelName = ensureNotBlank(modelName, "modelName");
        this.encoding = Encodings.newLazyEncodingRegistry().getEncodingForModel(modelName);
    }

    public int estimateTokenCountInText(String text) {
        return encoding.orElseThrow(unknownModelException())
                .countTokensOrdinary(text);
    }

    private int estimateTokenCountInToolParameters(ToolParameters parameters) {
        if (parameters == null) return 0;

        int tokenCount = 0;
        Map<String, Map<String, Object>> properties = parameters.properties();
        for (String property : properties.keySet()) {
            for (Map.Entry<String, Object> entry : properties.get(property).entrySet()) {
                if ("type".equals(entry.getKey())) {
                    tokenCount += 3;
                    tokenCount += estimateTokenCountInText(entry.getValue().toString());
                } else if ("description".equals(entry.getKey())) {
                    tokenCount += 3;
                    tokenCount += estimateTokenCountInText(entry.getValue().toString());
                } else if ("enum".equals(entry.getKey())) {
                    tokenCount -= 3;
                    for (Object enumValue : (Object[]) entry.getValue()) {
                        tokenCount += 3;
                        tokenCount += estimateTokenCountInText(enumValue.toString());
                    }
                }
            }
        }
        return tokenCount;
    }

    @Override
    public int estimateTokenCountInMessage(ChatMessage message) {
        return 0;
    }

    @Override
    public int estimateTokenCountInMessages(Iterable<ChatMessage> messages) {
        int tokenCount = 3;
        for (ChatMessage message : messages) {
            tokenCount += estimateTokenCountInMessage(message);
        }
        return tokenCount;
    }

    @Override
    public int estimateTokenCountInToolSpecifications(Iterable<ToolSpecification> toolSpecifications) {
        int tokenCount = 0;
        for (ToolSpecification toolSpecification : toolSpecifications) {
            tokenCount += estimateTokenCountInText(toolSpecification.name());
            tokenCount += estimateTokenCountInText(toolSpecification.description());
            tokenCount += estimateTokenCountInToolParameters(toolSpecification.parameters());
            tokenCount += 12;
        }
        tokenCount += 12;
        return tokenCount;
    }

    @Override
    public int estimateTokenCountInToolExecutionRequests(Iterable<ToolExecutionRequest> toolExecutionRequests) {
        return 0;
    }

    public List<Integer> encode(String text) {
        return encoding.orElseThrow(unknownModelException())
                .encodeOrdinary(text);
    }

    public List<Integer> encode(String text, int maxTokensToEncode) {
        return encoding.orElseThrow(unknownModelException())
                .encodeOrdinary(text, maxTokensToEncode).getTokens();
    }

    public String decode(List<Integer> tokens) {
        return encoding.orElseThrow(unknownModelException())
                .decode(tokens);
    }

    private Supplier<IllegalArgumentException> unknownModelException() {
        return () -> illegalArgument("Model '%s' is unknown to jtokkit", modelName);
    }

}
