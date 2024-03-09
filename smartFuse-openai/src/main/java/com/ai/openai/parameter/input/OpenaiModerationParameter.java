package com.ai.openai.parameter.input;

import com.ai.openai.endPoint.moderations.req.ModerationRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenaiModerationParameter implements Serializable {

    @Builder.Default
    private String model = ModerationRequest.Model.TEXT_MODERATION_LATEST.getName();

    @Getter
    @AllArgsConstructor
    public enum Model {
        TEXT_MODERATION_STABLE("text-moderation-stable"),
        TEXT_MODERATION_LATEST("text-moderation-latest"),
        ;

        private final String name;
    }

}
