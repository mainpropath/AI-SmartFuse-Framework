package com.ai.interfaces.chain;

/**
 * @Description: 链条
 **/
public interface Chain<Input, Output> {

    /**
     * 调用这条链条
     */
    Output run(Input input);

}
