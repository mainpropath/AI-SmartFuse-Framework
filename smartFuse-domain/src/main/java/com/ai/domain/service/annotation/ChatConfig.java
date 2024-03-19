package com.ai.domain.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface ChatConfig {

    /**
     * 设置要使用的存储器
     */
    Class<?> memory() default Void.class;

    /**
     * 设置要使用的审核模型
     *
     * @return
     */
    Class<?> moderate() default Void.class;

    /**
     * 设置要使用的对话模型
     */
    Class<?> chat() default Void.class;

}
