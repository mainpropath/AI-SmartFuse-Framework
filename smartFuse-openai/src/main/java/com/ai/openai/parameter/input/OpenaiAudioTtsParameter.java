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
public class OpenaiAudioTtsParameter implements Serializable {
    /**
     * 要使用的模型的 ID
     */
    @Builder.Default
    private String model = Model.tts_1.getModuleName();

    /**
     * 要为其生成音频的文本，最大长度为4096个字符
     */
    @NonNull
    private String input;

    /**
     * 声音样式
     */
    @NonNull
    private String voice;

    /**
     * 音频输入的格式，默认为mp3
     */
    @JsonProperty("response_format")
    private String responseFormat;

    /**
     * 音频的速度，0.25 到 4.0 之中选取一个数，数字越大速度越快。默认为1。
     */
    private String speed;

    @Getter
    @AllArgsConstructor
    public enum Model {
        tts_1("tts-1"), tts_1_hd("tts-1-hd");
        private String moduleName;
    }

    /**
     * 声音样式
     */
    @Getter
    @AllArgsConstructor
    public enum Voice {
        alloy("alloy"),
        echo("echo"),
        fable("fable"),
        onyx("onyx"),
        nova("nova"),
        shimmer("shimmer");
        private String voiceName;
    }
}
