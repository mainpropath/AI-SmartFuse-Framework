package com.ai.domain.prompt.impl;

import com.ai.common.util.PlaceHolderReplaceUtils;
import com.ai.domain.prompt.PromptTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.ai.common.util.ValidationUtils.ensureNotBlank;
import static com.ai.common.util.ValidationUtils.ensureNotNull;

/**
 * @Description: 普通提示词模板
 **/
public class SimplePromptTemplate implements PromptTemplate {

    private final Map<String, String> renderMap = new HashMap<>();
    private String template;
    private String promptName;

    public SimplePromptTemplate(String template, String promptName) {
        this.template = ensureNotBlank(template, "template");
        this.promptName = ensureNotBlank(promptName, "promptName");
    }

    public static String render(String prompt, Map<String, String> keys) {
        return PlaceHolderReplaceUtils.replaceWithMap(prompt, keys);
    }

    @Override
    public SimplePrompt apply(Map<String, String> keys) {
        return new SimplePrompt(PlaceHolderReplaceUtils.replaceWithMap(template, keys), new SimplePromptTemplate(this.template, this.promptName));
    }

    public void add(String key, String value) {
        this.renderMap.put(key, value);
    }

    public void addAll(Map<String, String> m) {
        this.renderMap.putAll(m);
    }

    @Override
    public String render(Map<String, String> keys) {
        return PlaceHolderReplaceUtils.replaceWithMap(template, keys);
    }

    public String render() {
        ensureNotNull(this.renderMap, "renderMap");
        return render(this.renderMap);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getPromptName() {
        return promptName;
    }

    public void setPromptName(String promptName) {
        this.promptName = promptName;
    }

    @Override
    public String toString() {
        return "SimplePromptTemplate{" +
                "template='" + template + '\'' +
                ", promptName='" + promptName + '\'' +
                ", renderMap=" + renderMap +
                '}';
    }
}
