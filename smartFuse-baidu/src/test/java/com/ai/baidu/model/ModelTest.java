package com.ai.baidu.model;


import com.ai.baidu.achieve.ApiData;
import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.achieve.standard.session.AggregationSession;
import com.ai.baidu.client.BaiduClient;
import com.ai.common.resp.AiResponse;
import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.data.images.Image;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.EmbeddingModel;
import com.ai.domain.model.ImageModel;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ModelTest {

    private AggregationSession aggregationSession;

    @Before
    public void test_model_before() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://aip.baidubce.com");
        ApiData apiData = ApiData.builder()
                .apiKey("kSwWPmJbaF8pzvi40KYILhII")
                .secretKey("3cf0xsAsmx4HtoNpuowNYaAU8gG77d1q")
                .appId("54863230")
                .build();
        configuration.setKeyList(Arrays.asList(apiData));
        configuration.setKeyStrategy(new FirstKeyStrategy<ApiData>());
        BaiduClient.SetConfiguration(configuration);
    }

    @Test
    public void test_chat() {
        ChatModel baiduChatModel = new BaiduChatModel();
        String res = baiduChatModel.generate("你好");
        System.out.println(res);
    }

    @Test
    public void test_embedding() {
        EmbeddingModel embeddingModel = new BaiduEmbeddingModel();
        AiResponse<Embedding> response = embeddingModel.embed("你好");
        System.out.println(response);
    }

    @Test
    public void test_image() {
        ImageModel imageModel = new BaiduImageModel();
        AiResponse<Image> imageAiResponse = imageModel.create("画一幅山水画");
        System.out.println(imageAiResponse.getData());
    }


}
