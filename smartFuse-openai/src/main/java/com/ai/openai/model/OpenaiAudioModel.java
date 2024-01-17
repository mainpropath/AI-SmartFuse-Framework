package com.ai.openai.model;


import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.AudioModel;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.audio.req.SttCompletionRequest;
import com.ai.openai.endPoint.audio.req.TtsCompletionRequest;
import com.ai.openai.endPoint.audio.resp.SttCompletionResponse;
import com.ai.openai.parameter.OpenaiAudioModelSttParameter;
import com.ai.openai.parameter.OpenaiAudioModelTtsParameter;
import com.ai.openai.parameter.input.OpenaiAudioSttParameter;
import com.ai.openai.parameter.input.OpenaiAudioTtsParameter;
import lombok.Data;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import java.io.File;

import static com.ai.common.exception.Constants.NULL;
import static com.ai.common.util.ValidationUtils.ensureNotNull;

/**
 * openai语音模型
 */
@Data
public class OpenaiAudioModel implements AudioModel {

    private Parameter<OpenaiAudioSttParameter> sttParameter;
    private Parameter<OpenaiAudioTtsParameter> ttsParameter;

    public OpenaiAudioModel() {
        this(new OpenaiAudioModelSttParameter(), new OpenaiAudioModelTtsParameter());
    }

    public OpenaiAudioModel(Parameter<OpenaiAudioSttParameter> sttParameter, Parameter<OpenaiAudioTtsParameter> ttsParameter) {
        this.sttParameter = ensureNotNull(sttParameter, "sttParameter");
        this.ttsParameter = ensureNotNull(ttsParameter, "ttsParameter");
    }

    @Override
    public void textToSpeech(String text, Callback<ResponseBody> callback) {
        OpenAiClient
                .getAggregationSession()
                .getAudioSession()
                .ttsCompletions(NULL, NULL, NULL, createRequestParameter(text), callback);
    }

    @Override
    public AiResponse<String> speechToText(File speech) {
        SttCompletionResponse response = OpenAiClient
                .getAggregationSession()
                .getAudioSession()
                .sttCompletions(NULL, NULL, NULL, createRequestParameter(speech));
        return createAiResponse(response);
    }

    private AiResponse<String> createAiResponse(SttCompletionResponse response) {
        return new AiResponse<>(response.getText(), null, FinishReason.SUCCESS);
    }

    private SttCompletionRequest createRequestParameter(File speech) {
        SttCompletionRequest request = SttCompletionRequest.builder().file(speech).build();
        BeanUtil.copyProperties(sttParameter.getParameter(), request);
        return request;
    }

    private TtsCompletionRequest createRequestParameter(String text) {
        TtsCompletionRequest request = TtsCompletionRequest.builder().input(text).build();
        BeanUtil.copyProperties(ttsParameter.getParameter(), request);
        return request;
    }

}
