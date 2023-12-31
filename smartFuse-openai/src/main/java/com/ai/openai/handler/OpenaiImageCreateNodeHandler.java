package com.ai.openai.handler;

import cn.hutool.core.util.StrUtil;
import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openai.model.OpenaiImageModel;

import java.util.List;

/**
 * @Description: 图片生成节点
 **/
public class OpenaiImageCreateNodeHandler implements ChainNodeHandler<String, List<ImageObject>> {

    private String msg;

    private OpenaiImageModel imageModel;

    public OpenaiImageCreateNodeHandler() {
        this(null, new OpenaiImageModel());
    }

    public OpenaiImageCreateNodeHandler(String msg) {
        this(msg, new OpenaiImageModel());
    }

    public OpenaiImageCreateNodeHandler(String msg, OpenaiImageModel imageModel) {
        this.msg = msg;
        this.imageModel = imageModel;
    }

    public List<ImageObject> execute() {
        return execute(msg);
    }

    @Override
    public List<ImageObject> execute(String parameter) {
        if (!StrUtil.isEmpty(parameter)) return imageModel.generate(parameter);
        throw new RuntimeException("Handler request parameter is empty.");
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
    }

}
