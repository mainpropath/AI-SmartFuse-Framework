package com.ai.interfaces.model;

/**
 * 模型接口，当做底层模型和链路的中间层，主要用于屏蔽底层模型调用方式的不同，上层只需关注接口，无需关注底层实现。
 **/
public interface Model<Input, Output> {

    /**
     * 模型执行
     */
    Output generate(Input message);

}

