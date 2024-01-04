package com.ai.domain.prompt;

import com.ai.domain.prompt.impl.SimplePrompt;
import com.ai.domain.prompt.impl.SimplePromptTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 提示词模板测试类
 **/
public class PromptTest {

    private SimplePromptTemplate simplePromptTemplate;

    @Before
    public void test_create_prompt_template() {
        // 提示词模板，需要替换的地方用{{}}包括，其中 key 为 money
        String promptTemplateString = "我有一辆价值{{money}}的车，它的品牌是：{{brand}}。";
        // 提示词的名称，标识这个提示词是干什么的
        String templateName = "汽车提示词";
        this.simplePromptTemplate = new SimplePromptTemplate(promptTemplateString, templateName);
    }

    @Test
    public void test_use_prompt_apply() {
        Map<String, String> map = new HashMap<>();
        map.put("money", "100万");
        map.put("brand", "宝马");
        // 传入一个包含关键字的Map，返回一个应用示例。其中会记录其对应的模板信息
        SimplePrompt apply = simplePromptTemplate.apply(map);
        System.out.println(apply);// SimplePrompt(text=我有一辆价值100万的车，它的品牌是：宝马。)
        System.out.println(apply.text());// 我有一辆价值100万的车，它的品牌是：宝马。
        System.out.println(apply.getPromptTemplate());// SimplePromptTemplate{template='我有一辆价值{{money}}的车，它的品牌是：{{brand}}。', promptName='汽车提示词', renderMap={}}
    }

    @Test
    public void test_use_prompt_render() {
        Map<String, String> map = new HashMap<>();
        map.put("money", "50万");
        map.put("brand", "奔驰");
        // 直接渲染，返回字符串
        String render = simplePromptTemplate.render(map);
        System.out.println(render);// 我有一辆价值50万的车，它的品牌是：奔驰。
    }

    @Test
    public void test_use_prompt_add_render() {
        // 将需要渲染的数据跟模板进行绑定
        simplePromptTemplate.add("money", "50万");
        simplePromptTemplate.add("brand", "奔驰");
        String render = simplePromptTemplate.render();
        System.out.println(render);
    }

}
