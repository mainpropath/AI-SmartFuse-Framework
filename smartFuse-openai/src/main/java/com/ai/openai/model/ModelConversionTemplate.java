package com.ai.openai.model;


/**
 * TODO 考虑用模板模式优化代码结构
 */
public abstract class ModelConversionTemplate {

    public final void run() {
        constructRequestParameters();
        initiateRequest();
        ParseReturnParameters();
    }

    abstract void constructRequestParameters();

    abstract void initiateRequest();

    abstract void ParseReturnParameters();

}
