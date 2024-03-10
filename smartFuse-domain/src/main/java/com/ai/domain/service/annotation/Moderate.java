package com.ai.domain.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用此注解标注在方法上，指定聊天所用的审核模型
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Moderate {

    Class<?> value() default Void.class;

}
