package com.ai.openai.service;


import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.service.AiServices;
import com.ai.domain.service.annotation.*;
import com.ai.openai.achieve.Configuration;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.memory.chat.OpenaiChatHistoryRecorder;
import com.ai.openai.model.OpenaiChatModel;
import com.ai.openai.model.OpenaiModerationModel;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;


public class ServiceTest {

    @Before
    public void test_service() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com");
        configuration.setKeyList(Arrays.asList("**********************"));
        configuration.setKeyStrategy(new FirstKeyStrategy<String>());
        configuration.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        OpenAiClient.SetConfiguration(configuration);
    }

    @Test
    public void test() {
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(new OpenaiChatModel())
                .build();

        String s1 = assistant.chat2("你好，我的名字叫小明", "1");
        System.out.println(s1);
        String s2 = assistant.chat2("我的名字叫什么？", "2");
        System.out.println(s2);

    }

    interface Assistant {

        @Moderate(OpenaiModerationModel.class)
        @Memory(OpenaiChatHistoryRecorder.class)
        @SystemMessage("你好，我是{{name}}")
        String chat(@UserMessage String message, @V("name") String name);

        String chat1(@UserMessage User user, String name);

        @Moderate(OpenaiModerationModel.class)
        @Memory(OpenaiChatHistoryRecorder.class)
        @SystemMessage("你是一位经验丰富的厨师")
        String chat2(@UserMessage String name, @MemoryId String id);
    }

    @Data
    @Prompt("你好，我是{{name}}，你是{{name2}}")
    class User {
        private String name;
        private String name2;
    }

}
