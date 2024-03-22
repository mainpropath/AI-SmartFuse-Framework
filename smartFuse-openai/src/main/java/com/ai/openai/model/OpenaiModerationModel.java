package com.ai.openai.model;


import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.moderation.Moderation;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.ModerationModel;
import com.ai.openai.achieve.standard.session.ModerationSession;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.moderations.Categories;
import com.ai.openai.endPoint.moderations.Result;
import com.ai.openai.endPoint.moderations.req.ModerationRequest;
import com.ai.openai.endPoint.moderations.resp.ModerationResponse;
import com.ai.openai.parameter.OpenaiModerationModelParameter;
import com.ai.openai.parameter.input.OpenaiModerationParameter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.ai.common.util.ValidationUtils.ensureNotEmpty;
import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.core.exception.Constants.NULL;

@Slf4j
public class OpenaiModerationModel implements ModerationModel {

    private final ModerationSession moderationSession = OpenAiClient.getAggregationSession().getModerationSession();
    private Parameter<OpenaiModerationParameter> parameter;

    public OpenaiModerationModel() {
        this.parameter = new OpenaiModerationModelParameter();
    }

    public OpenaiModerationModel(Parameter<OpenaiModerationParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    public Parameter<OpenaiModerationParameter> getParameter() {
        return parameter;
    }

    public void setParameter(Parameter<OpenaiModerationParameter> parameter) {
        this.parameter = ensureNotNull(parameter, "parameter");
    }

    @Override
    public AiResponse<List<Moderation>> moderate(List<String> messages) {
        ensureNotEmpty(messages, "messages");
        // 构造请求主要参数
        ModerationRequest request = ModerationRequest.builder().input(messages).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(parameter.getParameter(), request);
        // 发起请求获取结果
        ModerationResponse moderationResponse = moderationSession.moderationCompletions(NULL, NULL, NULL, request);
        // 解析结果封装为统一返回值
        List<Moderation> moderationList = analysisResult(moderationResponse.getResults());
        return AiResponse.R(moderationList, FinishReason.success());
    }

    private List<Moderation> analysisResult(List<Result> results) {
        ArrayList<Moderation> moderations = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            Result res = results.get(i);
            Categories categories = res.getCategories();
            Field[] declaredFields = categories.getClass().getDeclaredFields();
            boolean flag = false;// 标记是否有违规内容
            for (Field field : declaredFields) {
                try {
                    if ((field.getType() == boolean.class || field.getType() == Boolean.class) && field.getBoolean(categories)) {
                        field.setAccessible(true);// 设置可访问私有字段
                        moderations.add(Moderation.flagged(res.getContent(), field.getName()));
                        flag = true;
                        break;
                    }
                } catch (IllegalAccessException e) {
                    log.error("analysisResult error:{}", e.getMessage());
                }
            }
            if (!flag) moderations.add(Moderation.notFlagged());
        }
        return moderations;
    }

}
