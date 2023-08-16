package com.github.howwrite.treasure.jinxiu.domain.context;

public interface ResultContext<Response> extends Context {
    Response buildResponse();
}
