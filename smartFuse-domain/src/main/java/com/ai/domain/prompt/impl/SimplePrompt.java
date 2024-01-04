package com.ai.domain.prompt.impl;

import com.ai.domain.prompt.Prompt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePrompt implements Prompt {

    private String text;
    private SimplePromptTemplate promptTemplate;

    @Override
    public String text() {
        return text;
    }

}
