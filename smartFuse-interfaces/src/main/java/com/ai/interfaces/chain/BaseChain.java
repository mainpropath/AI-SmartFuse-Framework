package com.ai.interfaces.chain;

import java.util.List;

/**
 * @Description: 链条基础类
 **/
public abstract class BaseChain<T extends BaseChainNodeHandler> {

    public List<T> chainList;

    /**
     * 调用这条链条
     */
    public abstract void run();

}
