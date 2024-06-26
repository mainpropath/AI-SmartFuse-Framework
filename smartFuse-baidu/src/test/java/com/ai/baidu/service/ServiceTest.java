package com.ai.baidu.service;


import com.ai.baidu.achieve.ApiData;
import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.client.BaiduClient;
import com.ai.baidu.model.BaiduChatModel;
import com.ai.common.resp.AiResponse;
import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.service.AiServices;
import com.ai.domain.service.annotation.*;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;


public class ServiceTest {

    private Assistant assistant;

    @Before
    public void test_service() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://aip.baidubce.com");
        ApiData apiData = ApiData.builder()
                .apiKey("**************************")
                .secretKey("**************************")
                .appId("**************************")
                .build();
        configuration.setKeyList(Arrays.asList(apiData));
        configuration.setKeyStrategy(new FirstKeyStrategy<ApiData>());
        BaiduClient.SetConfiguration(configuration);
        // JDK动态代理
        assistant = AiServices.builder(Assistant.class).build();
    }

    @Test
    public void test_chat_with_template() {
        String res = assistant.chatWithTemplate("鱼香肉丝");
        System.out.println(res);
    }

    @Test
    public void test_chat_with_memory() {
        String s1 = assistant.chatWithMemory("请记住，我的名字叫小明。", 1);
        System.out.println(s1);// 好的，我会记住你的名字叫小明。有什么我可以帮助你的吗？
        String s2 = assistant.chatWithMemory("我的名字是什么？", 1);
        System.out.println(s2);// 你的名字是小明。
        String s3 = assistant.chatWithMemory("我的名字是什么？", 2);
        System.out.println(s3);// 抱歉，我无法知道您的名字，因为我是一个虚拟助手。您可以告诉我您的名字，我会很高兴为您称呼。
    }

    @Test
    public void test_simple_chat() {
        String message = "请你做一个自我介绍";
        AssistantMessage assistantMessage = assistant.simpleChat(message);
        System.out.println(assistantMessage);
    }

    @Test
    public void test_chat_with_scholar() {
        String msg = "堆排序的时间复杂度是多少？";
        String major = "计算机";
        String res = assistant.chatWithScholar(msg, major);
        System.out.println(res);
    }

    @Test
    public void test_Chat_with_nutrition_experts() {
        // 构建用户信息
        User user = new User();
        user.setName("小明");
        user.setAge(12);
        user.setHeight(150);
        // 发起对话
        String res = assistant.chatWithNutritionExperts(user);
        System.out.println(res);
    }

    @Test
    public void test_chat_with_moderate() {
        AiResponse<AssistantMessage> res = assistant.chatWithModerate("我要杀了你");
        System.out.println(res);
    }

    @ChatConfig(chat = BaiduChatModel.class)
//    @ChatConfig(chat = OpenaiChatModel.class, memory = SimpleChatHistoryRecorder.class, moderate = OpenaiModerationModel.class)
    interface Assistant {
        AssistantMessage simpleChat(@UserMessage String message);

        @SystemMessage("你是一位在{{major}}专业学识渊博的学者")
        String chatWithScholar(@UserMessage String message, @V("major") String major);

        @SystemMessage("你是一位营养学专家，请根据以下信息，回答以下提问。")
        String chatWithNutritionExperts(@UserMessage User userData);

        String chatWithMemory(@UserMessage String message, @MemoryId Integer memoryId);

        @UserMessage("你能教我{{dish}}这道菜的做法吗？")
        String chatWithTemplate(@V("dish") String dish);

        @Moderate
        AiResponse<AssistantMessage> chatWithModerate(@UserMessage String message);
    }

    @Data
    @Prompt("你好，我叫{{name}}，我的年龄是{{age}}岁，我的身高是{{height}}厘米，你能给我一些饮食上的建议吗？")
    class User {
        private String name;
        private Integer age;
        private Integer height;
    }


}
