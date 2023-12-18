package com.ai.domain.prompt;

import com.ai.common.util.PlaceHolderReplaceUtils;
import com.ai.interfaces.prompt.PromptTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

/**
 * @Description: 普通提示词模板
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePromptTemplate implements PromptTemplate {

    private String template;

    private String promptName;

    @Override
    public String render(Map<String, String> keys) {
        return PlaceHolderReplaceUtils.replaceWithMap(template, keys);
    }

    @Override
    public SimplePrompt apply(Map<String, String> keys) {
        return new SimplePrompt(PlaceHolderReplaceUtils.replaceWithMap(template, keys));
    }

    public String render(String value) {
        return render(Collections.singletonMap("it", value));
    }

}
