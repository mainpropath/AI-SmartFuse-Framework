package com.ai.interfaces.param;

/**
 * 参数标志，调用参数的设置
 **/
public interface Parameter<T> {

    T getParameter();

    void SetParameter(T parameter);

}
