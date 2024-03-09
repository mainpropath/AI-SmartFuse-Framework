package com.ai.openai.chain;

import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.chain.impl.ConversationalChain;
import com.ai.openai.achieve.Configuration;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.memory.chat.OpenaiChatHistoryRecorder;
import com.ai.openai.model.OpenaiChatModel;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

/**
 * 测试链路功能
 **/
public class ConversationalChainTest {

    private ConversationalChain conversationalChain;

    @Before
    public void test_create_conversational_chain() {
        // 设置配置信息
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com");
        configuration.setKeyList(Arrays.asList("************************"));
        configuration.setKeyStrategy(new FirstKeyStrategy<String>());
        configuration.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        OpenAiClient.SetConfiguration(configuration);
        // 创建一个记录器，记录器是不能重用的，即不能多个chain使用同一个记录器，否则就相当于多个会话公用同一个历史聊天记录。
        // 但是记录器对应的存储器，及记录器当中的ChatMemoryStore是可以重用的，及存在多个记录器使用同一个存储器。
        // 可以在创建时指定记录器,也可以直接创建使用默认的记录器，默认存储30条消息。
        this.conversationalChain = ConversationalChain.builder()
                .chatModel(new OpenaiChatModel())
                .historyRecorder(OpenaiChatHistoryRecorder.builder().build())
                .build();
    }

    @Test
    public void test_conversational_chain_run() {
        String res1 = conversationalChain.run("你好，请记住我的名字叫做小明");
        System.out.println(res1);// 你好，小明！很高兴认识你。
        String res2 = conversationalChain.run("我的名字是什么？");
        System.out.println(res2);// 你的名字是小明。
    }

}
