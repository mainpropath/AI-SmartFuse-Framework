package com.ai.openai.data.input;

import com.ai.openAi.endPoint.chat.ResponseFormat;
import com.ai.openAi.endPoint.chat.req.BaseChatCompletionRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenaiChatParam implements Serializable {

    /**
     * 要使用的模型的 ID
     */
    @Builder.Default
    private String model = BaseChatCompletionRequest.Model.GPT_3_5_TURBO.getModuleName();

    /**
     * 介于 -2.0 和 2.0 之间的数字，默认值为 0
     * 正值会根据新标记在文本中的现有频率来惩罚新标记从而降低模型逐字重复同一行的可能性
     */
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty;

    /**
     * 修改指定标记出现的可能性，默认值为 null
     */
    @JsonProperty("logit_bias")
    private Map logitBias;

    /**
     * 输出字符串限制；0 ~ 4096
     */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /**
     * 为每个提示生成的完成次数，默认值为 1
     */
    private Integer n;

    /**
     * 介于 -2.0 和 2.0 之间的数字，默认值为 0
     * 正值会根据新标记到目前为止是否出现在文本中来惩罚它们从而增加模型谈论新主题的可能性
     */
    @JsonProperty("presence_penalty")
    private double presencePenalty;

    /**
     * 指定模型必须输出的格式的对象。
     */
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    private Integer seed;

    /**
     * 停止输出标识，默认值为 null
     * 最多 4 个序列，API 将停止生成更多令牌
     */
    private List<String> stop;

    /**
     * 使用什么采样温度，介于 0 和 2 之间，默认值为 1
     * 较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更具集中性和确定性
     */
    private double temperature;

    /**
     * 默认值为 1
     * 温度采样的替代方法，称为核采样，其中模型考虑具有top_p概率质量的标记的结果。因此，0.1 表示仅考虑包含前 10% 概率质量的代币。
     */
    @JsonProperty("top_p")
    private Double topP;

    /**
     * 调用标识，避免重复调用
     */
    private String user;

    @Getter
    @AllArgsConstructor
    public enum Model {
        GPT_3_5_TURBO("gpt-3.5-turbo"), GPT_4("gpt-4"), GPT_4_32K("gpt-4-32k"),
        GPT_4_VISION_PREVIEW("gpt-4-vision-preview"),
        ;
        private String moduleName;
    }

}
