package com.github.howwrite.treasure.properties.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.PostConstruct;

/**
 * @author howwrite
 * @date 2020/10/7 9:53 下午
 */
@Slf4j
public abstract class BaseProperties {
    @PostConstruct
    public void showMe() {
        log.info(this.getClass() + " properties body is:{}", ToStringBuilder.reflectionToString(this));
    }
}
