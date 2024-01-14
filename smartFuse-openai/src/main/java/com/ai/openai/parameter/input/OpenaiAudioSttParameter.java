package com.ai.openai.parameter.input;

import com.ai.openAi.endPoint.audio.req.SttCompletionRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.File;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenaiAudioSttParameter {


    /**
     * 要转录的音频文件对象（不是文件名）
     * 采用以下格式之一：flac、mp3、mp4、mpeg、mpga、m4a、ogg、wav 或 webm
     */
    @NonNull
    private File file;

    /**
     * 使用的模型名
     */
    @NonNull
    @Builder.Default
    private String model = SttCompletionRequest.Model.whisper_1.getModuleName();

    /**
     * 音频的语言，以 ISO-639-1 格式提供输入语言将提高准确性和延迟。
     */
    private String language;

    /**
     * 一个可选文本，用于指导模型的样式或继续上一个音频片段。提示应与音频语言匹配。
     */
    private String prompt;

    /**
     * 脚本输出的格式
     */
    @JsonProperty("response_format")
    private String responseFormat;

    /**
     * 采样温度，介于 0 和 1 之间，默认值为 0 。
     * 较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更具针对性和确定性。
     * 如果设置为 0，模型将使用对数概率自动提高温度，直到达到某些阈值。
     */
    private Double temperature;

    @Getter
    @AllArgsConstructor
    public enum Model {
        whisper_1("whisper-1");
        private String moduleName;
    }


}
