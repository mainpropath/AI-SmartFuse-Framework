package com.ai.interfaces.chain.handler;

import java.util.Map;

/**
 * @Description: 链条节点
 **/
public interface ChainNodeHandler<Input, Output> {

    /**
     * 处理请求，传入Map，通用但难维护
     *
     * @param parameter 参数
     * @return 返回的结果
     */
    Output execute(Map<String, Object> parameter);


    /**
     * 处理请求，传入此节点所需的请求参数
     *
     * @param parameter 参数
     * @return 返回的结果
     */
    Output execute(Input parameter);

    /**
     * 参数转换
     *
     * @param parameter 参数
     * @return 转换过后的参数类型
     */
    Input convert(Map<String, Object> parameter);

}
