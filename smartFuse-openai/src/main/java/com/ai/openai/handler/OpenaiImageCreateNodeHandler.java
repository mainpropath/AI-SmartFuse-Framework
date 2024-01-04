package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openai.model.OpenaiImageModel;
import lombok.Data;

import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;

/**
 * 图片生成链路节点
 **/
@Data
public class OpenaiImageCreateNodeHandler implements ChainNodeHandler<String, List<ImageObject>> {

    private OpenaiImageModel imageModel;

    public OpenaiImageCreateNodeHandler() {
        this(new OpenaiImageModel());
    }

    public OpenaiImageCreateNodeHandler(OpenaiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public List<ImageObject> execute(String parameter) {
        ensureNotBlank(parameter, "image create parameter");
        return imageModel.generate(parameter);
    }

}
