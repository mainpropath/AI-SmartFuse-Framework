package com.ai.baidu.chain;

import com.ai.baidu.achieve.ApiData;
import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.client.BaiduClient;
import com.ai.baidu.model.BaiduChatModel;
import com.ai.core.strategy.impl.FirstKeyStrategy;
import com.ai.domain.chain.impl.ConversationalChain;
import com.ai.domain.memory.chat.impl.SimpleChatHistoryRecorder;
import org.junit.Before;
import org.junit.Test;

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
        configuration.setApiHost("https://aip.baidubce.com");
        ApiData apiData = ApiData.builder()
                .apiKey("**************************")
                .secretKey("**************************")
                .appId("**************************")
                .build();
        configuration.setKeyList(Arrays.asList(apiData));
        configuration.setKeyStrategy(new FirstKeyStrategy<ApiData>());
        BaiduClient.SetConfiguration(configuration);
        this.conversationalChain = ConversationalChain.builder()
                .chatModel(new BaiduChatModel())
                .historyRecorder(SimpleChatHistoryRecorder.builder().build())
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
