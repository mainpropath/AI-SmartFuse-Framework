package com.ai.baidu.model;


import com.ai.baidu.achieve.ApiData;
import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.achieve.standard.session.AggregationSession;
import com.ai.baidu.client.BaiduClient;
import com.ai.core.strategy.impl.FirstKeyStrategy;
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
        BaiduChatModel baiduChatModel = new BaiduChatModel();
        String s1 = baiduChatModel.generate("你好");
        System.out.println(s1);
    }


}
