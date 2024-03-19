package com.ai.domain.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 通过此注解来标识用户信息
 */
@Retention(RUNTIME)
@Target({METHOD, PARAMETER})
public @interface UserMessage {

    String[] value() default "";

    String delimiter() default "\n";

}
