package com.github.howwrite.treasure.web.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author howwrite
 * @date 2020/10/7 12:38 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WebRestException extends RuntimeException {
    private static final long serialVersionUID = 8919621441431800289L;

    private Object[] args;

    public WebRestException(String errorMessage, Object... args) {
        super(errorMessage);
        this.args = args;
    }
}
