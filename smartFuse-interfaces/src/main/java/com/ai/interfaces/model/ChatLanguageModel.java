package com.ai.interfaces.model;

/**
 * @Description: 模型
 **/
public interface ChatLanguageModel<Output> {

    Output generate(String userMessage);

}

