package com.ai.domain.service;

import cn.hutool.core.util.StrUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.common.util.Exceptions;
import com.ai.domain.data.message.AssistantMessage;
import com.ai.domain.data.message.ChatMessage;
import com.ai.domain.data.moderation.Moderation;
import com.ai.domain.memory.chat.ChatHistoryRecorder;
import com.ai.domain.model.ChatModel;
import com.ai.domain.model.ModerationModel;
import com.ai.domain.prompt.impl.SimplePromptTemplate;
import com.ai.domain.service.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
class DefaultAiServices<T> extends AiServices<T> {

    DefaultAiServices(AiServiceContext context) {
        super(context);
    }

    /**
     * 检查@Prompt注解
     */
    public static String prompt(Class<?> clazz, Object arg) {
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
                    log.error("@Prompt Check error");
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

    public static void verifyModerationIfNeeded(Future<Moderation> moderationFuture) {
        if (moderationFuture != null) {
            try {
                Moderation moderation = moderationFuture.get();
                if (moderation.getFlagged()) {
                    throw Exceptions.runtime(String.format("Text \"%s\" violates content policy", moderation.getFlaggedText()));
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 解析@MemoryId注解，只有第一个@MemoryId注解才会生效
     */
    public String memoryId(Parameter[] parameters, Class<?>[] parameterTypes, Object[] args) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameters[i].isAnnotationPresent(MemoryId.class)
                    && (String.class.isAssignableFrom(parameterTypes[i])
                    || Integer.class.isAssignableFrom(parameterTypes[i])
                    || Long.class.isAssignableFrom(parameterTypes[i])
                    || int.class.isAssignableFrom(parameterTypes[i])
                    || long.class.isAssignableFrom(parameterTypes[i]))) {
                synchronized (context.getChatHistoryRecorder()) {
                    context.getChatHistoryRecorder().setId(String.valueOf(args[i]));
                    return String.valueOf(args[i]);
                }
            }
        }
        return null;
    }

    /**
     * 检查@SystemMessage注解
     *
     * @param method     对应的方法
     * @param parameters 参数信息
     * @param args       参数值
     */
    public com.ai.domain.data.message.SystemMessage systemMessage(Method method, Parameter[] parameters, Object[] args) {
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
            return com.ai.domain.data.message.SystemMessage.message(SimplePromptTemplate.render(systemMessageTemplate, variables));
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
    public com.ai.domain.data.message.UserMessage UserMessageOnMethod(Method method, Parameter[] parameters, Object[] args) {
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
            return com.ai.domain.data.message.UserMessage.message(SimplePromptTemplate.render(userMessageTemplate, variables));
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
    public com.ai.domain.data.message.UserMessage userMessageOnArgs(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameters[i].isAnnotationPresent(UserMessage.class)) {
                // 当@UserMessage修饰参数时，如果是字符串类型，直接返回UserMessage
                if (String.class.isAssignableFrom(parameterTypes[i])) {
                    return com.ai.domain.data.message.UserMessage.message((String) args[i]);
                }
                // 当@UserMessage修饰的是某个被@Prompt注解的对象时，解析成模板返回映射后的字符串
                String prompt = prompt(parameterTypes[i], args[i]);
                if (StrUtil.isNotEmpty(prompt))
                    return com.ai.domain.data.message.UserMessage.message(prompt);
            }
        }
        return null;
    }

    private void chatConfig(ChatConfig chatConfig) {
        if (chatConfig == null) return;
        try {
            Class<?> chat = chatConfig.chat();
            Class<?> memory = chatConfig.memory();
            Class<?> moderate = chatConfig.moderate();
            if (chat != null) context.setChatModel((ChatModel) chat.getDeclaredConstructor().newInstance());
            if (memory != null && !memory.equals(void.class))
                context.setChatHistoryRecorder((ChatHistoryRecorder) memory.getDeclaredConstructor().newInstance());
            if (moderate != null && !moderate.equals(void.class))
                context.setModerationModel((ModerationModel) moderate.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T build() {
        Class<?> serviceClass = context.getAiServiceClass();
        chatConfig(serviceClass.getAnnotation(ChatConfig.class));
        performValidation();
        Object proxyInstance = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{serviceClass}, new InvocationHandler() {

            private final ExecutorService executor = Executors.newCachedThreadPool();

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                // 系统层面的 hashCode() equals() 等方法不需要被代理。
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                }
                Parameter[] parameters = method.getParameters();
                Class<?>[] parameterTypes = method.getParameterTypes();
                String memoryId = memoryId(parameters, parameterTypes, args);
                com.ai.domain.data.message.SystemMessage systemMessage = systemMessage(method, parameters, args);
                com.ai.domain.data.message.UserMessage userMessageOnMethod = UserMessageOnMethod(method, parameters, args);
                com.ai.domain.data.message.UserMessage userMessage = userMessageOnMethod == null ? userMessageOnArgs(method, args) : userMessageOnMethod;
                if (userMessage == null) {
                    throw Exceptions.illegalArgument("Unable to locate chat parameters");
                }
                Future<Moderation> moderationFuture = triggerModerationIfNeeded(method, userMessage.getText());
                ChatHistoryRecorder chatHistoryRecorder = context.getChatHistoryRecorder();
                ChatModel chatModel = context.getChatModel();
                AssistantMessage data;
                if (memoryId != null && chatHistoryRecorder != null) {// 不为空，进行消息的记录
                    if (systemMessage != null) chatHistoryRecorder.add(systemMessage);
                    chatHistoryRecorder.add(userMessage); // 添加用户消息
                    data = chatModel.generate(chatHistoryRecorder.getCurrentMessages()).getData();// 发起请求
                    chatHistoryRecorder.add(data);// 记录结果
                } else {// 为空，相当于单次对话
                    ArrayList<ChatMessage> chatMessages = new ArrayList<>();
                    if (systemMessage != null) chatMessages.add(systemMessage);
                    chatMessages.add(userMessage);
                    data = chatModel.generate(chatMessages).getData();
                }
                verifyModerationIfNeeded(moderationFuture);
                return ServiceOutputParser.parse(AiResponse.R(data, FinishReason.success()), method.getReturnType());
            }

            private Future<Moderation> triggerModerationIfNeeded(Method method, String userMessage) {
                if (method.isAnnotationPresent(Moderate.class) && context.getModerationModel() != null) {
                    return executor.submit(() -> context.getModerationModel().moderate(userMessage).getData());
                }
                return null;
            }
        });

        return (T) proxyInstance;
    }

}
