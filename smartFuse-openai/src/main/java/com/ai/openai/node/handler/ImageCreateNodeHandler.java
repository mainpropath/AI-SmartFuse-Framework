package com.ai.openai.node.handler;

import cn.hutool.core.bean.BeanUtil;
import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openAi.endPoint.images.req.CreateImageRequest;
import com.ai.openai.client.OpenAiClient;

import java.util.List;
import java.util.Map;

import static com.ai.openAi.common.Constants.NULL;

/**
 * @Description: 图片创造节点
 **/
public class ImageCreateNodeHandler implements ChainNodeHandler<CreateImageRequest, List<ImageObject>> {

    @Override
    public List<ImageObject> execute(Map<String, Object> parameter) {
        return this.execute(convert(parameter));
    }

    @Override
    public List<ImageObject> execute(CreateImageRequest parameter) {
        return OpenAiClient.getAggregationSession().getImageSession().createImageCompletions(NULL, NULL, NULL, parameter);
    }

    @Override
    public CreateImageRequest convert(Map<String, Object> parameter) {
        return BeanUtil.fillBeanWithMap(parameter, new CreateImageRequest(), false);
    }

}
