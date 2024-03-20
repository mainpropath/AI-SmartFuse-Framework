package com.ai.openai.model;

import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.images.Image;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ImageModel;
import com.ai.openai.achieve.standard.session.ImageSession;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.images.ImageObject;
import com.ai.openai.endPoint.images.req.CreateImageRequest;
import com.ai.openai.parameter.OpenaiImageModelParameter;
import com.ai.openai.parameter.input.OpenaiImageParameter;

import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.core.exception.Constants.NULL;

/**
 * 图片生成模型
 **/
public class OpenaiImageModel implements ImageModel {

    private final ImageSession imageSession = OpenAiClient.getAggregationSession().getImageSession();
    private Parameter<OpenaiImageParameter> parameter;

    public OpenaiImageModel() {
        this.parameter = new OpenaiImageModelParameter();
    }

    public OpenaiImageModel(Parameter<OpenaiImageParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    public static Image ImageObj2Image(ImageObject imageObject) {
        return new Image(imageObject.getUrl());
    }

    public Parameter<OpenaiImageParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<OpenaiImageParameter> parameter) {
        this.parameter = parameter;
    }

    @Override
    public AiResponse<Image> create(String message) {
        ensureNotBlank(message, "message");
        List<ImageObject> imageObjects = imageSession.createImageCompletions(NULL, NULL, NULL, createRequestParameter(message));
        return createAiResponse(imageObjects);
    }

    private AiResponse<Image> createAiResponse(List<ImageObject> imageObjects) {
        return AiResponse.R(ImageObj2Image(imageObjects.get(0)), FinishReason.success());
    }

    private CreateImageRequest createRequestParameter(String message) {
        CreateImageRequest request = CreateImageRequest.baseBuild(message);
        BeanUtil.copyProperties(parameter.getParameter(), request);
        return request;
    }

}
