package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import com.ai.openai.model.OpenaiEmbeddingModel;
import lombok.Data;

import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotEmpty;

/**
 * 嵌入链路节点
 **/
@Data
public class OpenaiEmbeddingNodeHandler implements ChainNodeHandler<String, EmbeddingObject> {

    private OpenaiEmbeddingModel embeddingModel;

    public OpenaiEmbeddingNodeHandler() {
        this(new OpenaiEmbeddingModel());
    }

    public OpenaiEmbeddingNodeHandler(OpenaiEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public static EmbeddingObject executeByStatic(String parameter) {
        return new OpenaiEmbeddingNodeHandler().execute(parameter);
    }

    @Override
    public EmbeddingObject execute(String parameter) {
        ensureNotBlank(parameter, "embedding string");
        return embeddingModel.generate(parameter).getData().get(0);
    }

    public List<EmbeddingObject> execute(List<String> parameter) {
        ensureNotEmpty(parameter, "embedding list");
        return embeddingModel.generate(parameter).getData();
    }


}
