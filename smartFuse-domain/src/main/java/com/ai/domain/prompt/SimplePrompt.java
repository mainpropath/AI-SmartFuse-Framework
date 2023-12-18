package com.ai.domain.prompt;

import com.ai.interfaces.prompt.Prompt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePrompt implements Prompt {

    private String text;

    @Override
    public String text() {
        return text;
    }

}
