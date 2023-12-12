package com.ai.interfaces.chain;

import lombok.Data;

import java.util.Map;

/**
 * @Description: 链条节点基础类
 **/
@Data
public abstract class BaseChainNodeHandler<REQ , RESP> {

    /**
     * 下一个链节点
     */
    protected BaseChainNodeHandler nextHandler;

    /**
     * 处理请求
     *
     * @param parameter 参数
     * @return 返回的结果
     */
    public abstract RESP handleRequest(Map<String, Object> parameter);

    /**
     * 参数转换
     *
     * @param parameter 参数
     * @return 转换过后的参数类型
     */
    public abstract REQ convert(Map<String, Object> parameter);

    /**
     * 调用下一个链节点
     *
     * @param parameter 参数
     */
    public void next(Map<String, Object> parameter) {
        this.nextHandler.handleRequest(parameter);
    }
}
