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
import java.util.stream.Collectors;

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

    public static List<Image> imageObjList2ImageList(List<ImageObject> imageObjectList) {
        return imageObjectList.stream()
                .map(imageObject -> imageObj2Image(imageObject))
                .collect(Collectors.toList());
    }

    public static Image imageObj2Image(ImageObject imageObject) {
        return Image.from(imageObject.getUrl(), imageObject.getB64Json());
    }

    public Parameter<OpenaiImageParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<OpenaiImageParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<List<Image>> create(String prompt, String size, String style, int n) {
        ensureNotBlank(prompt, "prompt");
        // 构造请求主要参数
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(prompt).size(size).style(style).n(n).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(parameter.getParameter(), request);
        // 发起请求获取结果
        List<ImageObject> imageObjectList = imageSession.createImageCompletions(NULL, NULL, NULL, request);
        // 转换结果为统一返回值
        return AiResponse.R(imageObjList2ImageList(imageObjectList), FinishReason.success());
    }

}
