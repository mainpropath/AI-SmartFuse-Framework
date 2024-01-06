package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.usage.AiResponse;
import com.ai.common.usage.TokenUsage;
import com.ai.domain.data.images.Image;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ImageModel;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openAi.endPoint.images.req.CreateImageRequest;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.parameter.OpenaiImageModelParameter;
import com.ai.openai.parameter.input.OpenaiImageParameter;
import lombok.Data;

import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.openAi.common.Constants.NULL;
import static com.ai.openai.converter.BeanConverter.ImageObj2Image;

/**
 * 图片生成模型
 **/
@Data
public class OpenaiImageModel implements ImageModel {

    private Parameter<OpenaiImageParameter> parameter;

    public OpenaiImageModel() {
        this.parameter = new OpenaiImageModelParameter();
    }

    public OpenaiImageModel(Parameter<OpenaiImageParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<Image> create(String message) {
        ensureNotBlank(message, "message");
        CreateImageRequest request = createRequestParameter(message);
        List<ImageObject> imageObjects = OpenAiClient.getAggregationSession()
                .getImageSession()
                .createImageCompletions(NULL, NULL, NULL, request);
        return createAiResponse(imageObjects);
    }

    private AiResponse<Image> createAiResponse(List<ImageObject> imageObjects) {
        Image image = ImageObj2Image(imageObjects.get(0));
        return new AiResponse<>(image, new TokenUsage(), "");
    }

    private CreateImageRequest createRequestParameter(String message) {
        CreateImageRequest request = CreateImageRequest.BuildBaseCreateImageRequest(message);
        BeanUtil.copyProperties(parameter.getParameter(), request);
        return request;
    }


}
