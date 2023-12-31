package com.ai.openai.handler;

import com.ai.common.util.ParamCheckUtils;
import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.embeddings.EmbeddingObject;
import com.ai.openai.model.OpenaiEmbeddingModel;
import lombok.Data;

import java.util.List;

/**
 * @Description: 嵌入节点
 **/
@Data
public class OpenaiEmbeddingNodeHandler implements ChainNodeHandler<String, List<EmbeddingObject>> {

    private OpenaiEmbeddingModel embeddingModel;

    public OpenaiEmbeddingNodeHandler() {
        this(new OpenaiEmbeddingModel());
    }

    public OpenaiEmbeddingNodeHandler(OpenaiEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @Override
    public List<EmbeddingObject> execute(String parameter) {
        ParamCheckUtils.checkStr(parameter, "embedding parameter cannot be empty.");
        return embeddingModel.generate(parameter).getData();
    }

}
