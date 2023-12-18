package com.ai.interfaces.prompt;

import java.util.Map;

/**
 * @Description: 提示词模板
 **/
public interface PromptTemplate {

    String render(Map<String, String> keys);

    Prompt apply(Map<String, String> keys);

}
