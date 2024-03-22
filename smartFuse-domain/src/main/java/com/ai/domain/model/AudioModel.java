package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import java.io.File;

/**
 * 文字和语音处理
 */
public interface AudioModel {

    /**
     * 文字转语音
     */
    void textToSpeech(String text, Callback<ResponseBody> callback);

    /**
     * 语音转文字
     */
    AiResponse<String> speechToText(File speech);

}
