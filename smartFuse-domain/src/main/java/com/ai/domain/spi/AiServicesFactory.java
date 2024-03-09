package com.ai.domain.spi;

import com.ai.domain.service.AiServiceContext;
import com.ai.domain.service.AiServices;

public interface AiServicesFactory {

    <T> AiServices<T> create(AiServiceContext context);
}
