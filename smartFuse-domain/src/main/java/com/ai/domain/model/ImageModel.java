package com.ai.domain.model;


import com.ai.common.usage.AiResponse;
import com.ai.domain.data.images.Image;

public interface ImageModel {

    AiResponse<Image> create(String message);

}
