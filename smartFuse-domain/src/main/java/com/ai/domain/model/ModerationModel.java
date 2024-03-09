package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import com.ai.domain.data.moderation.Moderation;

import java.util.List;

public interface ModerationModel {

    AiResponse<Moderation> moderate(String message);

    AiResponse<List<Moderation>> moderate(List<String> messages);

}
