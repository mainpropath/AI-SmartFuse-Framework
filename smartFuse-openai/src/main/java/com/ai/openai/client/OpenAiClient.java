package com.ai.openai.client;

import com.ai.openAi.achieve.Configuration;
import com.ai.openAi.achieve.defaults.DefaultOpenAiSessionFactory;
import com.ai.openAi.achieve.defaults.strategy.FirstKeyStrategy;
import com.ai.openAi.achieve.standard.OpenAiSessionFactory;
import com.ai.openAi.achieve.standard.interfaceSession.AggregationSession;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

/**
 * @Description: openAi请求客户端
 **/
public class OpenAiClient {

    private static volatile AggregationSession aggregationSession;

    public static AggregationSession getAggregationSession() {
        if (aggregationSession == null) {
            synchronized (OpenAiClient.class) {
                if (aggregationSession == null) {
                    Configuration configuration = new Configuration();
                    configuration.setApiHost("https://api.openai.com");
//                    configuration.setKeyList(Arrays.asList("填入你的API Key"));
                    configuration.setKeyList(Arrays.asList("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"));
                    configuration.setKeyStrategy(new FirstKeyStrategy());
                    configuration.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
                    OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
                    aggregationSession = factory.openAggregationSession();
                }
            }
        }
        return aggregationSession;
    }


}
