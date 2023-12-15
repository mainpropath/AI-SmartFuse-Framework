package com.ai.interfaces.chain;

import java.util.List;

/**
 * @Description: 链条
 **/
public abstract class Chain<T extends ChainNodeHandler> {

    public List<T> chainList;

    /**
     * 调用这条链条
     */
    public abstract void run();

}
