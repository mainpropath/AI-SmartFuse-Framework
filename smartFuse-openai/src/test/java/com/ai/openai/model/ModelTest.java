package com.ai.openai.model;


import com.ai.common.resp.AiResponse;
import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.data.moderation.Moderation;
import com.ai.openai.achieve.Configuration;
import com.ai.openai.client.OpenAiClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelTest {

    @Before
    public void test_model_before() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com");
        configuration.setKeyList(Arrays.asList("************************"));
        configuration.setKeyStrategy(new FirstKeyStrategy<String>());
        configuration.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        OpenAiClient.SetConfiguration(configuration);
    }

    @Test
    public void test_moderation_model() {
        OpenaiModerationModel openaiModerationModel = new OpenaiModerationModel();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("你好");
        strings.add("我要杀了你");
        AiResponse<List<Moderation>> moderate = openaiModerationModel.moderate(strings);
        System.out.println(moderate.getData());
    }

}
