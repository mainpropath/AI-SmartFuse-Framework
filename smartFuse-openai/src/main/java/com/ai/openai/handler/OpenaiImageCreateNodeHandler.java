package com.ai.openai.handler;

import com.ai.common.util.ParamCheckUtils;
import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openai.model.OpenaiImageModel;
import lombok.Data;

import java.util.List;

/**
 * @Description: 图片生成节点
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
        ParamCheckUtils.checkStr(parameter, "handler request parameter is empty.");
        return imageModel.generate(parameter);
    }

}
