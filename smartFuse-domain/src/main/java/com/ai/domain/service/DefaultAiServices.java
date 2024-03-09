package com.ai.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class DefaultAiServices<T> extends AiServices<T> {

    private static final int MAX_SEQUENTIAL_TOOL_EXECUTIONS = 10;
    private final Logger log = LoggerFactory.getLogger(AiServices.class);

    DefaultAiServices(AiServiceContext context) {
        super(context);
    }

    public T build() {

        performBasicValidation();

        for (Method method : context.aiServiceClass.getMethods()) {
//            if (method.isAnnotationPresent(Moderate.class) && context.moderationModel == null) {
//                throw new RuntimeException("The @Moderate annotation is present, but the moderationModel is not set up. " +
//                        "Please ensure a valid moderationModel is configured before using the @Moderate annotation.");
//            }
        }

        Object proxyInstance = Proxy.newProxyInstance(
                context.aiServiceClass.getClassLoader(),
                new Class<?>[]{context.aiServiceClass},
                new InvocationHandler() {

                    private final ExecutorService executor = Executors.newCachedThreadPool();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                        return null;
                    }

                });

        return (T) proxyInstance;
    }

}
