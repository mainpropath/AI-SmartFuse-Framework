package com.ai.domain.model;


import com.ai.common.resp.AiResponse;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import java.io.File;

public interface AudioModel {

    void textToSpeech(String text, Callback<ResponseBody> callback);

    AiResponse<String> speechToText(File speech);

}
