package com.ai.interfaces.chain;

import lombok.Data;

import java.util.Map;

/**
 * @Description: 链条节点
 **/
@Data
public abstract class ChainNodeHandler<INPUT, OUTPUT> {

    /**
     * 处理请求
     *
     * @param parameter 参数
     * @return 返回的结果
     */
    public abstract OUTPUT execute(Map<String, Object> parameter);

    /**
     * 参数转换
     *
     * @param parameter 参数
     * @return 转换过后的参数类型
     */
    public abstract INPUT convert(Map<String, Object> parameter);

}
