package com.ai.baidu.model;


import cn.hutool.core.bean.BeanUtil;
import com.ai.baidu.achieve.standard.session.ImageSession;
import com.ai.baidu.client.BaiduClient;
import com.ai.baidu.endPoint.images.ImageData;
import com.ai.baidu.endPoint.images.req.ImageRequest;
import com.ai.baidu.parameter.BaiduImageModelParameter;
import com.ai.baidu.parameter.input.BaiduImageParameter;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.images.Image;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ImageModel;

import java.util.List;
import java.util.stream.Collectors;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotNull;

public class BaiduImageModel implements ImageModel {

    private final ImageSession imageSession = BaiduClient.getAggregationSession().getImageSession();
    private Parameter<BaiduImageParameter> parameter;

    public BaiduImageModel() {
        this.parameter = new BaiduImageModelParameter();
    }

    public BaiduImageModel(Parameter<BaiduImageParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    public static List<Image> imageDataList2ImageList(List<ImageData> imageDataList) {
        return imageDataList.stream()
                .map(imageData -> imageData2Image(imageData))
                .collect(Collectors.toList());
    }

    public static Image imageData2Image(ImageData imageData) {
        return Image.b64Json(imageData.getB64Image());
    }

    public Parameter<BaiduImageParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<BaiduImageParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<List<Image>> create(String prompt, String size, String style, int n) {
        ensureNotBlank(prompt, "prompt");
        // 构造请求主要参数
        ImageRequest request = ImageRequest.builder()
                .prompt(prompt).size(size).style(style).n(n).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(parameter.getParameter(), request);
        // 发起请求获取结果
        List<ImageData> imageDataList = imageSession.text2image(request).getData();
        // 转换结果为统一返回值
        return AiResponse.R(imageDataList2ImageList(imageDataList), FinishReason.success());
    }

}
