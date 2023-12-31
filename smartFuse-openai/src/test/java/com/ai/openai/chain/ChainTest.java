package com.ai.openai.chain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description: 测试链路功能
 **/
@Slf4j
public class ChainTest {

    /**
     * 测试使用对话链
     */
    @Test
    public void test_conversationalChain() {
        OpenaiConversationalChain chain = OpenaiConversationalChain.builder().build();
        String str1 = chain.run("你好，请记住我的名字叫做小明");
        log.info("回答结果：{}", str1);// 你好，小明！很高兴认识你。
        String str2 = chain.run("我的名字是什么？");
        log.info("回答结果：{}", str2);// 你的名字是小明。
    }

}
