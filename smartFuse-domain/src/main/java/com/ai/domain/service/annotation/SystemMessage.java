package com.ai.domain.service.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface SystemMessage {

    String[] value() default "";

    String delimiter() default "\n";

}
