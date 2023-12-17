package com.ai.interfaces.prompt;

import java.util.Map;

/**
 * @Description: 提示词模板工厂
 **/
public interface PromptTemplateFactory {
    PromptTemplateFactory.Template create(PromptTemplateFactory.Input var1);

    interface Template {
        String render(Map<String, Object> var1);
    }

    interface Input {
        String getTemplate();

        String getName();
    }
}
