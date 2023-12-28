package com.ai.interfaces.chain;

/**
 * @Description: 链条，一个链条当中可以有多个链条节点。
 **/
public interface Chain<Input, Output> {

    /**
     * 调用这条链条
     */
    Output run(Input input);

}
