package com.ai.domain.service.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 此注解标注在类上，表示这是一个模板类。
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Prompt {

    String[] value();

    String delimiter() default "\n";

}
