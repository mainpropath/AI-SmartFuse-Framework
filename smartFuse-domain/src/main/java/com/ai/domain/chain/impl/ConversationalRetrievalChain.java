package com.ai.domain.chain.impl;

import com.ai.domain.chain.Chain;
import com.ai.domain.data.embedding.EmbeddingMatch;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.UserMessage;
import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.memory.embedding.EmbeddingStoreRetriever;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.EmbeddingModel;
import com.ai.domain.prompt.impl.SimplePromptTemplate;
import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * 文档检索聊天链
 */
@Data
@Builder
public class ConversationalRetrievalChain implements Chain<String, String> {

    private EmbeddingModel embeddingModel;
    private ChatModel chatModel;
    private ChatHistoryRecorder historyRecorder;
    @Builder.Default
    private SimplePromptTemplate promptTemplate = new SimplePromptTemplate("Answer the following question to the best of your ability: {{question}}\\n\\nBase your answer on the following information:\\n{{information}}", "default template");
    private EmbeddingStoreRetriever<EmbeddingMatch> retriever;

    @Override
    public String run(String s) {
        List<EmbeddingMatch> relevant = retriever.findRelevant(embeddingModel.embed(s).getData());
        StringBuilder stringBuilder = new StringBuilder();
        for (EmbeddingMatch embeddingMatch : relevant) {
            stringBuilder.append(embeddingMatch.getEmbedding().getContent());
        }
        promptTemplate.add("question", s);
        promptTemplate.add("information", stringBuilder.toString());
        historyRecorder.add(new UserMessage(promptTemplate.render()));
        AssistantMessage data = chatModel.generate(historyRecorder.getCurrentMessages()).getData();
        historyRecorder.add(data);
        return data.text();
    }
}
