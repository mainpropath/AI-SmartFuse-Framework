package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.images.Image;

import java.util.List;

public interface ImageModel {

    /**
     * 根据 prompt 生成一张图片
     */
    default AiResponse<Image> create(String prompt) {
        AiResponse<List<Image>> listAiResponse = create(prompt, 1);
        return AiResponse.R(listAiResponse.getData().get(0), FinishReason.success());
    }

    /**
     * 根据 prompt 生成 n 张图片，n 的大小根据不同的模型而定
     * n 的大小根据不同的模型而定
     */
    default AiResponse<List<Image>> create(String prompt, int n) {
        return create(prompt, null, null, n);
    }

    /**
     * 根据 prompt 生成 n 张图片，大小为 size，风格为 style 的图片
     * n/size/style 的值根据不同模型而定
     */
    AiResponse<List<Image>> create(String prompt, String size, String style, int n);

}
