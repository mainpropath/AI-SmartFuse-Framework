package com.ai.openai.client;


import com.ai.openai.achieve.Configuration;
import com.ai.openai.achieve.defaults.DefaultOpenAiSessionFactory;
import com.ai.openai.achieve.standard.interfaceSession.AggregationSession;

import static com.ai.common.util.ValidationUtils.ensureNotNull;

/**
 * @Description: openAi请求客户端
 **/
public class OpenAiClient {

    private static AggregationSession aggregationSession;

    private static Configuration configuration;

    public static void SetConfiguration(Configuration configuration) {
        ensureNotNull(configuration, "configuration");
        aggregationSession = new DefaultOpenAiSessionFactory(configuration).openAggregationSession();
    }

    public static Configuration GetConfiguration() {
        return configuration;
    }

    public static AggregationSession getAggregationSession() {
        return aggregationSession;
    }

}
