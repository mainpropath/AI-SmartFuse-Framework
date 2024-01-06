package com.ai.domain.model;

public interface ModelTemplate<Req, Resp> {

    Resp createAiResponse(Req request);
}
