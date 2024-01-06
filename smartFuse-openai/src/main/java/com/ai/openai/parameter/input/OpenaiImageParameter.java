package com.ai.openai.parameter.input;

import com.ai.openAi.endPoint.images.req.CreateImageRequest;
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
public class OpenaiImageParameter implements Serializable {

    /**
     * 用于图像生成的模型
     * <p>
     * 默认为 dall-e-2
     */
    @Builder.Default
    private String model = CreateImageRequest.Model.DALL_E_3.getName();

    /**
     * 要生成的图像数。必须介于 1 和 10 之间，dall-e-3只能为1。
     */
    private Integer n;

    /**
     * 将生成的图像的质量。 创建具有更精细细节和更高一致性的图像。
     *
     * @see CreateImageRequest.Quality
     */
    private String quality;

    /**
     * 返回生成的图像的格式：url、b64_json
     */
    @JsonProperty("response_format")
    private String responseFormat;

    /**
     * 图片尺寸，默认值：1024x1024
     * dall-e-2支持：256x256, 512x512, or 1024x1024
     * dall-e-3支持：1024x1024, 1792x1024, or 1024x1792
     */
    private String size;

    /**
     * 生成的图像的样式。
     * 此参数仅仅dall-e-3,取值范围：vivid、natural
     * 默认值：vivid
     *
     * @see CreateImageRequest.Style
     */
    private String style;

    /**
     * 代表最终用户的唯一标识符
     */
    private String user;

    /**
     * 图片生成模型
     */
    @Getter
    @AllArgsConstructor
    public enum Model {
        DALL_E_2("dall-e-2"),
        DALL_E_3("dall-e-3"),
        ;
        private final String name;
    }

    /**
     * 生成图片质量
     */
    @Getter
    @AllArgsConstructor
    public enum Quality {
        STANDARD("standard"),
        HD("hd"),
        ;
        private final String name;
    }

    /**
     * 生成图片风格
     */
    @Getter
    @AllArgsConstructor
    public enum Style {
        VIVID("vivid"),
        NATURAL("natural"),
        ;
        private final String name;
    }
}
