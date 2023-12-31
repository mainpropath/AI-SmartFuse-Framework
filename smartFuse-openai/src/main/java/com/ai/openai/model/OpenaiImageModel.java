package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.interfaces.message.ChatMessage;
import com.ai.interfaces.model.ChatLanguageModel;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openAi.endPoint.images.req.CreateImageRequest;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.param.OpenaiImageModelParameter;
import lombok.Data;

import java.util.List;

import static com.ai.openAi.common.Constants.NULL;

/**
 * @Description: 图片生成模型
 **/
@Data
public class OpenaiImageModel implements ChatLanguageModel<List<ImageObject>> {

    private OpenaiImageModelParameter parameter;

    public OpenaiImageModel() {
        this.parameter = new OpenaiImageModelParameter();
    }

    public OpenaiImageModel(OpenaiImageModelParameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public List<ImageObject> generate(String userMessage) {
        CreateImageRequest createImageRequest = CreateImageRequest.BuildBaseCreateImageRequest(userMessage);
        BeanUtil.copyProperties(parameter.getParameter(), createImageRequest);
        return OpenAiClient.getAggregationSession().getImageSession().createImageCompletions(NULL, NULL, NULL, createImageRequest);
    }

    @Override
    public List<ImageObject> generate(List<ChatMessage> messageList) {
        return null;
    }
}