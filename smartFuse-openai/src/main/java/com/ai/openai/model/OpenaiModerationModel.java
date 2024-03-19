package com.ai.openai.model;


import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.moderation.Moderation;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ModerationModel;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.moderations.Categories;
import com.ai.openai.endPoint.moderations.Result;
import com.ai.openai.endPoint.moderations.req.ModerationRequest;
import com.ai.openai.endPoint.moderations.resp.ModerationResponse;
import com.ai.openai.parameter.OpenaiModerationModelParameter;
import com.ai.openai.parameter.input.OpenaiModerationParameter;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ai.common.util.ValidationUtils.*;
import static com.ai.core.exception.Constants.NULL;

@Data
public class OpenaiModerationModel implements ModerationModel {

    private Parameter<OpenaiModerationParameter> parameter;

    public OpenaiModerationModel() {
        this.parameter = new OpenaiModerationModelParameter();
    }

    public OpenaiModerationModel(Parameter<OpenaiModerationParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<Moderation> moderate(String message) {
        ensureNotBlank(message, "message");
        AiResponse<List<Moderation>> moderate = moderate(Arrays.asList(message));
        return new AiResponse<>(moderate.getData().get(0), null, FinishReason.SUCCESS);
    }

    @Override
    public AiResponse<List<Moderation>> moderate(List<String> messages) {
        ensureNotEmpty(messages, "messages");
        ModerationResponse moderationResponse = OpenAiClient
                .getAggregationSession()
                .getModerationSession()
                .moderationCompletions(NULL, NULL, NULL, createRequestParameter(messages));
        return createAiResponse(messages, moderationResponse);
    }

    private ModerationRequest createRequestParameter(List<String> messages) {
        ModerationRequest request = ModerationRequest.builder().input(messages).build();
        BeanUtil.copyProperties(parameter.getParameter(), request);
        return request;
    }

    private AiResponse<List<Moderation>> createAiResponse(List<String> messages, ModerationResponse moderationResponse) {
        ArrayList<Moderation> moderations = new ArrayList<>();
        List<Result> results = moderationResponse.getResults();
        for (int i = 0; i < results.size(); i++) {
            Result res = results.get(i);
            Categories categories = res.getCategories();
            Field[] declaredFields = categories.getClass().getDeclaredFields();
            boolean flag = false;
            for (Field field : declaredFields) {
                // 确保字段是布尔类型
                if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    try {
                        field.setAccessible(true);// 设置可访问私有字段
                        if (field.getBoolean(categories)) {// 获取字段的值
                            moderations.add(Moderation.flagged(messages.get(i), field.getName()));
                            flag = true;
                            break;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!flag) moderations.add(Moderation.notFlagged());
        }
        return new AiResponse<>(moderations, null, FinishReason.SUCCESS);
    }
}
