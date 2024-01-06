package com.ai.domain.document.tokenizer;

import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.tools.ToolExecutionRequest;
import com.ai.domain.tools.ToolSpecification;

import java.util.ArrayList;
import java.util.List;

import static com.ai.domain.tools.ToolSpecifications.toolSpecificationsFrom;
import static java.util.Collections.singletonList;


public interface Tokenizer {

    int estimateTokenCountInText(String text);

    int estimateTokenCountInMessage(ChatMessage message);

    int estimateTokenCountInMessages(Iterable<ChatMessage> messages);

    default int estimateTokenCountInTools(Object objectWithTools) {
        return estimateTokenCountInTools(singletonList(objectWithTools));
    }

    default int estimateTokenCountInTools(Iterable<Object> objectsWithTools) {
        List<ToolSpecification> toolSpecifications = new ArrayList<>();
        objectsWithTools.forEach(objectWithTools ->
                toolSpecifications.addAll(toolSpecificationsFrom(objectWithTools)));
        return estimateTokenCountInToolSpecifications(toolSpecifications);
    }

    default int estimateTokenCountInToolSpecification(ToolSpecification toolSpecification) {
        return estimateTokenCountInToolSpecifications(singletonList(toolSpecification));
    }

    int estimateTokenCountInToolSpecifications(Iterable<ToolSpecification> toolSpecifications);

    int estimateTokenCountInToolExecutionRequests(Iterable<ToolExecutionRequest> toolExecutionRequests);

    default int estimateTokenCountInForcefulToolExecutionRequest(ToolExecutionRequest toolExecutionRequest) {
        return estimateTokenCountInToolExecutionRequests(singletonList(toolExecutionRequest));
    }
}
