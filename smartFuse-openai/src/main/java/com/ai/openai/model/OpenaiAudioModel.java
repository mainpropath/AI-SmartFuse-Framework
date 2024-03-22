package com.ai.openai.model;


import cn.hutool.core.bean.BeanUtil;
import com.ai.common.resp.AiResponse;
import com.ai.common.resp.finish.FinishReason;
import com.ai.domain.data.parameter.Parameter;
import com.ai.domain.model.AudioModel;
import com.ai.openai.achieve.standard.session.AudioSession;
import com.ai.openai.client.OpenAiClient;
import com.ai.openai.endPoint.audio.req.SttCompletionRequest;
import com.ai.openai.endPoint.audio.req.TtsCompletionRequest;
import com.ai.openai.endPoint.audio.resp.SttCompletionResponse;
import com.ai.openai.parameter.OpenaiAudioModelSttParameter;
import com.ai.openai.parameter.OpenaiAudioModelTtsParameter;
import com.ai.openai.parameter.input.OpenaiAudioSttParameter;
import com.ai.openai.parameter.input.OpenaiAudioTtsParameter;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import java.io.File;

import static com.ai.common.util.ValidationUtils.ensureNotNull;
import static com.ai.core.exception.Constants.NULL;

/**
 * openai语音模型
 */
public class OpenaiAudioModel implements AudioModel {

    private final AudioSession audioSession = OpenAiClient.getAggregationSession().getAudioSession();
    private Parameter<OpenaiAudioSttParameter> sttParameter;
    private Parameter<OpenaiAudioTtsParameter> ttsParameter;

    public OpenaiAudioModel() {
        this(new OpenaiAudioModelSttParameter(), new OpenaiAudioModelTtsParameter());
    }

    public OpenaiAudioModel(Parameter<OpenaiAudioSttParameter> sttParameter, Parameter<OpenaiAudioTtsParameter> ttsParameter) {
        this.sttParameter = ensureNotNull(sttParameter, "sttParameter");
        this.ttsParameter = ensureNotNull(ttsParameter, "ttsParameter");
    }

    public Parameter<OpenaiAudioSttParameter> getSttParameter() {
        return sttParameter;
    }

    public void setSttParameter(Parameter<OpenaiAudioSttParameter> sttParameter) {
        this.sttParameter = ensureNotNull(sttParameter, "sttParameter");
    }

    public Parameter<OpenaiAudioTtsParameter> getTtsParameter() {
        return ttsParameter;
    }

    public void setTtsParameter(Parameter<OpenaiAudioTtsParameter> ttsParameter) {
        this.ttsParameter = ensureNotNull(ttsParameter, "ttsParameter");
    }

    @Override
    public void textToSpeech(String text, Callback<ResponseBody> callback) {
        // 构造请求主要参数
        TtsCompletionRequest request = TtsCompletionRequest.builder().input(text).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(ttsParameter.getParameter(), request);
        // 通过回调函数处理结果
        audioSession.ttsCompletions(NULL, NULL, NULL, request, callback);
    }

    @Override
    public AiResponse<String> speechToText(File speech) {
        // 构造请求主要参数
        SttCompletionRequest request = SttCompletionRequest.builder().file(speech).build();
        // 填充请求配置属性
        BeanUtil.copyProperties(sttParameter.getParameter(), request);
        // 发起请求获取结果
        SttCompletionResponse response = audioSession.sttCompletions(NULL, NULL, NULL, request);
        // 转换结果为统一返回值
        return AiResponse.R(response.getText(), FinishReason.success());
    }

}
