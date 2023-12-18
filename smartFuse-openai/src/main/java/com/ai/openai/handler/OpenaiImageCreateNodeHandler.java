package com.ai.openai.handler;

import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openai.model.OpenaiImageModel;
import com.ai.openai.param.OpenaiImageModelParameter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 图片生成节点
 **/
public class OpenaiImageCreateNodeHandler implements ChainNodeHandler<String, List<ImageObject>> {

    private String msg;

    private OpenaiImageModel imageModel;

    private OpenaiImageModelParameter parameter;

    public OpenaiImageCreateNodeHandler() {
        this(null, new OpenaiImageModel(), new OpenaiImageModelParameter());
    }

    public OpenaiImageCreateNodeHandler(String msg) {
        this(msg, new OpenaiImageModel(), new OpenaiImageModelParameter());
    }

    public OpenaiImageCreateNodeHandler(String msg, OpenaiImageModelParameter parameter) {
        this(msg, new OpenaiImageModel(), parameter);
    }

    public OpenaiImageCreateNodeHandler(String msg, OpenaiImageModel imageModel, OpenaiImageModelParameter parameter) {
        this.msg = msg;
        this.imageModel = imageModel;
        this.parameter = parameter;
        update();
    }

    public List<ImageObject> execute() {
        return execute(msg);
    }

    @Override
    public List<ImageObject> execute(String parameter) {
        return imageModel.generate(parameter);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OpenaiImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(OpenaiImageModel imageModel) {
        this.imageModel = imageModel;
        update();
    }

    public OpenaiImageModelParameter getParameter() {
        return parameter;
    }

    public void setParameter(OpenaiImageModelParameter parameter) {
        this.parameter = parameter;
        update();
    }

    private void update() {
        this.imageModel.setParameter(parameter);
    }
}
