package com.ai.openai.parameter.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenaiEmbeddingParameter implements Serializable {

    /**
     * 要使用的模型的 ID。
     */
    @Builder.Default
    private String model = OpenaiEmbeddingParameter.Model.TEXT_EMBEDDING_ADA_002.getModelName();

    /**
     * 要返回嵌入的格式。
     */
    @JsonProperty("encoding_format")
    private String encodingFormat;

    @Getter
    @AllArgsConstructor
    public enum Model {
        TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
        ;
        private final String modelName;
    }

}
