package com.ai.interfaces.chain.handler;

/**
 * 链路节点，只负责处理某一项任务。
 **/
public interface ChainNodeHandler<Input, Output> {

    /**
     * 处理请求，传入此节点所需的请求参数
     *
     * @param parameter 参数
     * @return 返回的结果
     */
    Output execute(Input parameter);

}
