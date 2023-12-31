package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.util.ParamCheckUtils;
import com.ai.interfaces.model.Model;
import com.ai.openAi.endPoint.embeddings.req.EmbeddingCompletionRequest;
import com.ai.openAi.endPoint.embeddings.resp.EmbeddingCompletionResponse;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.param.OpenaiEmbeddingModelParameter;

import java.util.Arrays;
import java.util.List;

import static com.ai.openAi.common.Constants.NULL;

/**
 * @Description: 文本嵌入模型
 **/
public class OpenaiEmbeddingModel implements Model<String, EmbeddingCompletionResponse> {

    private OpenaiEmbeddingModelParameter parameter;

    public OpenaiEmbeddingModel() {
        this(new OpenaiEmbeddingModelParameter());
    }

    public OpenaiEmbeddingModel(OpenaiEmbeddingModelParameter openaiEmbeddingModelParameter) {
        this.parameter = openaiEmbeddingModelParameter;
    }

    @Override
    public EmbeddingCompletionResponse generate(String message) {
        ParamCheckUtils.checkStr(message, "content cannot be empty");
        return this.generate(Arrays.asList(message));
    }

    public EmbeddingCompletionResponse generate(List<String> msgList) {
        ParamCheckUtils.checkList(msgList, "embedding list cannot be empty");
        EmbeddingCompletionRequest embeddingCompletionRequest = EmbeddingCompletionRequest.BuildBaseEmbeddingCompletionRequest(msgList);
        BeanUtil.copyProperties(parameter.getParameter(), embeddingCompletionRequest);
        return OpenAiClient.getAggregationSession().getEmbeddingSession().embeddingCompletions(NULL, NULL, NULL, embeddingCompletionRequest);
    }


}
