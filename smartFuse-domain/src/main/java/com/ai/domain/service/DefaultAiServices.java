package com.ai.domain.service;

import cn.hutool.core.util.StrUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.common.util.Exceptions;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.ModerationModel;
import com.ai.domain.prompt.impl.SimplePromptTemplate;
import com.ai.domain.service.annotation.*;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

class DefaultAiServices<T> extends AiServices<T> {

    DefaultAiServices(AiServiceContext context) {
        super(context);
    }

    /**
     * 检查@Prompt注解
     */
    public static String analyzePromptAnnotation(Class<?> clazz, Object arg) {
        com.ai.domain.service.annotation.Prompt prompt = clazz.getAnnotation(com.ai.domain.service.annotation.Prompt.class);
        if (prompt != null) {
            Map<String, String> map = new HashMap<>();
            // 获取设置的模板
            String promptTemplate = String.join(prompt.delimiter(), prompt.value());
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(arg).toString());
                } catch (Exception e) {
                }
            }
            return SimplePromptTemplate.render(promptTemplate, map);
        }
        return null;
    }

    /**
     * 解析@V注解，得到一个Map, key 为字段名，value 为值
     */
    private static Map<String, String> getPromptTemplateVariables(Object[] args, Parameter[] parameters) {
        Map<String, String> variables = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            V varAnnotation = parameters[i].getAnnotation(V.class);
            if (varAnnotation != null) {
                String variableName = varAnnotation.value();
                Object variableValue = args[i];
                variables.put(variableName, variableValue.toString());
            }
        }
        return variables;
    }

    public void analyzeMemoryIdAnnotation(Parameter[] parameters, Class<?>[] parameterTypes, Object[] args) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameters[i].isAnnotationPresent(MemoryId.class) && String.class.isAssignableFrom(parameterTypes[i])) {
                context.getChatHistoryRecorder().setId((String) args[i]);
                return;
            }
        }
    }

    /**
     * 检查@Memory注解
     *
     * @param method 对应的方法
     */
    public void analyzeMemoryAnnotation(Method method) throws Exception {
        if (context.getChatHistoryRecorder() != null) {
            return;
        }
        Memory memory = method.getAnnotation(Memory.class);
        if (memory != null) {
            boolean flag = false;
            Class<?> recorder = memory.value();
            // 判断注解设置的对象是否实现了ChatHistoryRecorder接口
            for (Class<?> intf : recorder.getInterfaces()) {
                if (intf.equals(ChatHistoryRecorder.class)) {
                    flag = true;
                    break;
                }
            }
            // 如果未实现，抛出异常
            if (!flag)
                throw new RuntimeException("The passed in parameter does not implement the ChatHistoryRecorder interface");
            context.setChatHistoryRecorder((ChatHistoryRecorder) recorder.getDeclaredConstructor().newInstance());
        }
    }

    /**
     * 检查@Moderate注解
     *
     * @param method 对应的方法
     */
    public void analyzeModerateAnnotation(Method method) throws Exception {
        if (context.getModerationModel() != null) {
            return;
        }
        Moderate moderate = method.getAnnotation(Moderate.class);
        if (moderate != null) {
            boolean flag = false;
            Class<?> moderation = moderate.value();
            for (Class<?> intf : moderation.getInterfaces()) {
                if (intf.equals(ModerationModel.class)) {
                    flag = true;
                    break;
                }
            }
            // 如果未实现，抛出异常
            if (!flag)
                throw new RuntimeException("The passed in parameter does not implement the ModerationModel interface");
            context.setModerationModel((ModerationModel) moderation.getDeclaredConstructor().newInstance());
        }

    }

    /**
     * 检查@SystemMessage注解
     *
     * @param method     对应的方法
     * @param parameters 参数信息
     * @param args       参数值
     * @return
     */
    public com.ai.domain.data.message.SystemMessage analyzeSystemMessageAnnotation(Method method, Parameter[] parameters, Object[] args) {
        // 解析方法参数当中被@V修饰的参数
        Map<String, String> variables = getPromptTemplateVariables(args, parameters);
        SystemMessage annotation = method.getAnnotation(SystemMessage.class);
        if (annotation != null) {
            // 得到系统信息模板
            String systemMessageTemplate = String.join(annotation.delimiter(), annotation.value());
            if (systemMessageTemplate.isEmpty()) {
                throw Exceptions.illegalArgument("@SystemMessage's template cannot be empty");
            }
            // 将字段信息进行映射
            return new com.ai.domain.data.message.SystemMessage(SimplePromptTemplate.render(systemMessageTemplate, variables));
        }
        return null;
    }

    /**
     * 检查方法上的@UserMessage注解
     *
     * @param method 对应的方法
     * @param args   参数值
     * @return UserMessage
     */
    public com.ai.domain.data.message.UserMessage analyzeUserMessageAnnotationOnMethod(Method method, Parameter[] parameters, Object[] args) {
        // 解析方法参数当中被@V修饰的参数
        Map<String, String> variables = getPromptTemplateVariables(args, parameters);
        UserMessage annotation = method.getAnnotation(UserMessage.class);
        if (annotation != null) {
            // 得到用户信息模板
            String userMessageTemplate = String.join(annotation.delimiter(), annotation.value());
            if (userMessageTemplate.isEmpty()) {
                throw Exceptions.illegalArgument("@UserMessage's template cannot be empty");
            }
            // 将字段信息进行映射
            return new com.ai.domain.data.message.UserMessage(SimplePromptTemplate.render(userMessageTemplate, variables));
        }
        return null;
    }

    /**
     * 检查方法参数上的@UserMessage注解
     *
     * @param method 对应的方法
     * @param args   参数值
     * @return UserMessage
     */
    public com.ai.domain.data.message.UserMessage analyzeUserMessageAnnotationOnArg(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameters[i].isAnnotationPresent(UserMessage.class)) {
                // 当@UserMessage修饰参数时，如果是字符串类型，直接返回UserMessage
                if (String.class.isAssignableFrom(parameterTypes[i])) {
                    return new com.ai.domain.data.message.UserMessage((String) args[i]);
                }
                // 当@UserMessage修饰的是某个被@Prompt注解的对象时，解析成模板返回映射后的字符串
                String prompt = analyzePromptAnnotation(parameterTypes[i], args[i]);
                if (StrUtil.isNotEmpty(prompt))
                    return new com.ai.domain.data.message.UserMessage(prompt);
            }
        }
        return null;
    }

    public T build() {
        performValidation();
        Object proxyInstance = Proxy.newProxyInstance(context.getAiServiceClass().getClassLoader(), new Class<?>[]{context.getAiServiceClass()}, new InvocationHandler() {
//  todo 审核模型的调用
//            private final ExecutorService executor = Executors.newCachedThreadPool();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                // 系统层面的 hashCode() equals() 等方法不需要被代理。
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                Parameter[] parameters = method.getParameters();
                Class<?>[] parameterTypes = method.getParameterTypes();
                // 解析各个注解
                analyzeMemoryAnnotation(method);
                analyzeMemoryIdAnnotation(parameters, parameterTypes, args);
                analyzeModerateAnnotation(method);
                com.ai.domain.data.message.SystemMessage systemMessage = analyzeSystemMessageAnnotation(method, parameters, args);
                com.ai.domain.data.message.UserMessage userMessageOnMethod = analyzeUserMessageAnnotationOnMethod(method, parameters, args);
                com.ai.domain.data.message.UserMessage userMessage = userMessageOnMethod == null ? analyzeUserMessageAnnotationOnArg(method, args) : userMessageOnMethod;
                if (userMessage == null) {
                    throw Exceptions.illegalArgument("Unable to locate chat parameters");
                }
                ChatHistoryRecorder chatHistoryRecorder = context.getChatHistoryRecorder();
                ChatModel chatModel = context.getChatModel();
                // 添加系统消息
                chatHistoryRecorder.add(systemMessage);
                // 添加用户消息
                chatHistoryRecorder.add(userMessage);
                // 发起请求
                AssistantMessage data = chatModel.generate(chatHistoryRecorder.getCurrentMessages()).getData();
                // 记录结果
                chatHistoryRecorder.add(data);
                AiResponse<AssistantMessage> response = new AiResponse<>(data, null, FinishReason.SUCCESS);
                // 将结果转换为接口所需的返回类型
                return ServiceOutputParser.parse(response, method.getReturnType());
            }
        });

        return (T) proxyInstance;
    }

}
