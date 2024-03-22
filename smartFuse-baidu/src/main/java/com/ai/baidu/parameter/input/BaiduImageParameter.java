package com.ai.baidu.parameter.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaiduImageParameter implements Serializable {


    /**
     * 生成图片数量，说明：
     * · 默认值为1
     * · 取值范围为1-4
     * · 单次生成的图片较多及请求较频繁可能导致请求超时
     */
    private Integer n;

    /**
     * 迭代轮次，说明：
     * · 默认值为20
     * · 取值范围为10-50
     */
    private Integer steps;

    /**
     * 采样方式，默认值：Euler a，可选值如下(释义参考)：
     * · Euler
     * · Euler a
     * · DPM++ 2M
     * · DPM++ 2M Karras
     * · LMS Karras
     * · DPM++ SDE
     * · DPM++ SDE Karras
     * · DPM2 a Karras
     * · Heun
     * · DPM++ 2M SDE
     * · DPM++ 2M SDE Karras
     * · DPM2
     * · DPM2 Karras
     * · DPM2 a
     * · LMS
     */
    @JsonProperty("sampler_index")
    private String samplerIndex;

    /**
     * 随机种子，说明：
     * · 不设置时，自动生成随机数
     * · 取值范围 [0, 4294967295]
     */
    private Integer seed;

    /**
     * 提示词相关性，说明：默认值为5，取值范围0-30
     */
    @JsonProperty("cfg_scale")
    private Double cfgScale;

    /**
     * 表示最终用户的唯一标识符
     */
    @JsonProperty("user_id")
    private String userId;

}
