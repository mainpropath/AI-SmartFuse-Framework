package com.ai.baidu.client;

import com.ai.baidu.achieve.Configuration;
import com.ai.baidu.achieve.defaults.DefaultBaiduSessionFactory;
import com.ai.baidu.achieve.standard.session.AggregationSession;

import static com.ai.common.util.ValidationUtils.ensureNotNull;


public class BaiduClient {

    private static AggregationSession aggregationSession;

    private static Configuration configuration;

    public static void SetConfiguration(Configuration configuration) {
        ensureNotNull(configuration, "configuration");
        aggregationSession = new DefaultBaiduSessionFactory(configuration).openAggregationSession();
    }

    public static Configuration GetConfiguration() {
        return configuration;
    }

    public static AggregationSession getAggregationSession() {
        return aggregationSession;
    }

}
