package com.ai.openai.chain;

import com.ai.domain.document.TextSegment;
import com.ai.domain.prompt.impl.SimplePromptTemplate;
import com.ai.interfaces.chain.Chain;
import com.ai.openai.handler.OpenaiEmbeddingNodeHandler;
import com.ai.openai.handler.OpenaiTextDialogueNodeHandler;
import com.ai.openai.memory.chat.OpenaiChatHistoryRecorder;
import com.ai.openai.memory.chat.message.OpenaiAssistantMessage;
import com.ai.openai.memory.chat.message.OpenaiUserMessage;
import com.ai.openai.memory.embedding.EmbeddingMatch;
import com.ai.openai.memory.embedding.OpenaiEmbeddingStoreRetriever;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * 文档检索聊天链
 */
@Data
@Builder
public class OpenaiConversationalRetrievalChain implements Chain<String, String> {
    @Builder.Default
    private OpenaiEmbeddingNodeHandler embeddingNodeHandler = new OpenaiEmbeddingNodeHandler();
    @Builder.Default
    private OpenaiTextDialogueNodeHandler textDialogueNodeHandler = new OpenaiTextDialogueNodeHandler();
    @Builder.Default
    private OpenaiChatHistoryRecorder historyRecorder = OpenaiChatHistoryRecorder.builder().build();
    @Builder.Default
    private SimplePromptTemplate promptTemplate = new SimplePromptTemplate("Answer the following question to the best of your ability: {{question}}\\n\\nBase your answer on the following information:\\n{{information}}", "default template");
    @Builder.Default
    private OpenaiEmbeddingStoreRetriever<TextSegment> retriever = OpenaiEmbeddingStoreRetriever.<TextSegment>builder().build();

    @Override
    public String run(String s) {
        List<EmbeddingMatch<TextSegment>> relevant = retriever.findRelevant(embeddingNodeHandler.execute(s));
        StringBuilder stringBuilder = new StringBuilder();
        for (EmbeddingMatch<TextSegment> embeddingMatch : relevant) {
            stringBuilder.append(embeddingMatch.getMetadata().getText());
        }
        promptTemplate.add("question", s);
        promptTemplate.add("information", stringBuilder.toString());
        historyRecorder.add(OpenaiUserMessage.builder().content(promptTemplate.render()).build());
        OpenaiAssistantMessage openaiAssistantMessage = textDialogueNodeHandler.execute(historyRecorder.getCurrentMessages());
        historyRecorder.add(openaiAssistantMessage);
        return openaiAssistantMessage.content();
    }
}
