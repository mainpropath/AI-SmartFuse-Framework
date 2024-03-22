package com.ai.baidu.model;


import com.ai.baidu.achieve.ApiData;
import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.achieve.standard.session.AggregationSession;
import com.ai.baidu.client.BaiduClient;
import com.ai.common.resp.AiResponse;
import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.data.embedding.Embedding;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.EmbeddingModel;
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
                .apiKey("******************")
                .secretKey("******************")
                .appId("******************")
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


}
