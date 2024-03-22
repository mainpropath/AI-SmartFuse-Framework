package com.ai.baidu.parameter.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaiduChatParameter implements Serializable {

    /**
     * （1）较高的数值会使输出更加随机，而较低的数值会使其更加集中和确定
     * （2）默认0.8，范围 (0, 1.0]，不能为0
     */
    private Double temperature;

    /**
     * （1）影响输出文本的多样性，取值越大，生成文本的多样性越强
     * （2）默认0.8，取值范围 [0, 1.0]
     */
    @JsonProperty("top_p")
    private Double topP;

    /**
     * 通过对已生成的token增加惩罚，减少重复生成的现象。说明：
     * （1）值越大表示惩罚越大
     * （2）默认1.0，取值范围：[1.0, 2.0]
     */
    @JsonProperty("penalty_score")
    private Double penaltyScore;

    /**
     * 是否以流式接口的形式返回数据，默认false
     */
    private Boolean stream;

    /**
     * 模型人设，主要用于人设设定，例如，你是xxx公司制作的AI助手，说明：
     * （1）长度限制，最后一个message的content长度（即此轮对话的问题）和system字段总内容不能超过20000个字符，且不能超过5120 tokens
     */
    private String system;

    /**
     * 生成停止标识，当模型生成结果以stop中某个元素结尾时，停止文本生成。说明：
     * （1）每个元素长度不超过20字符
     * （2）最多4个元素
     */
    private List<String> stop;

    /**
     * 否强制关闭实时搜索功能，默认false，表示不关闭
     */
    @JsonProperty("disable_search")
    private Boolean disableSearch;

    /**
     * 是否开启上角标返回，说明：
     * （1）开启后，有概率触发搜索溯源信息search_info，search_info内容见响应参数介绍
     * （2）默认false，不开启
     */
    @JsonProperty("enable_citation")
    private Boolean enableCitation;

    /**
     * 指定模型最大输出token数，范围[2, 2048]
     */
    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;

    /**
     * 指定响应内容的格式，说明：
     * （1）可选值：
     * json_object：以json格式返回，可能出现不满足效果情况
     * text：以文本格式返回
     * （2）如果不填写参数response_format值，默认为text
     */
    @JsonProperty("response_format")
    private String responseFormat;

    /**
     * 表示最终用户的唯一标识符
     */
    @JsonProperty("user_id")
    private String userId;

}
