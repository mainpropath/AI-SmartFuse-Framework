package com.ai.domain.prompt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

/**
 * @Description: 提示词模板测试类
 **/
@Slf4j
public class PromptTest {

    @Test
    public void test_use_prompt() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "内容1");
        map.put("key2", "内容2");
        map.put("it", "内容3");
        SimplePromptTemplate simplePromptTemplate = new SimplePromptTemplate("{{key1}},{{key2}},{{it}}", "测试提示词");
        SimplePrompt apply = simplePromptTemplate.apply(map);
        log.info("测试结果：{}", apply.text());
        log.info("测试结果：{}", simplePromptTemplate.render(map));
        log.info("测试结果：{}", simplePromptTemplate.render("内容4"));
    }

}
