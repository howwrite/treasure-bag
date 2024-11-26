package com.github.howwrite.treasure.spring.component;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

public abstract class BaseProperties {
    private static final Logger log = LoggerFactory.getLogger(BaseProperties.class);

    public BaseProperties() {
    }

    @PostConstruct
    public void showMe() {
        log.info(this.getClass() + " properties body is:{}", ToStringBuilder.reflectionToString(this));
    }
}