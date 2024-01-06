package com.ai.domain.chain;

/**
 * 链路接口，一个链条当中可以有多个链路节点。
 **/
public interface Chain<Input, Output> {

    /**
     * 运行这条链路
     */
    Output run(Input input);

}
