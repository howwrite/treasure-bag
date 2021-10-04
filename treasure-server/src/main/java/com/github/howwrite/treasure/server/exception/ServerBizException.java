package com.github.howwrite.treasure.server.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常，会透出给前端异常信息
 *
 * @author howwrite
 * @date 2020/10/7 9:47 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerBizException extends ServerException {
    private static final long serialVersionUID = 1861119396443594724L;

    /**
     * 默认提示文案
     */
    private String   defaultMessage;
    /**
     * 提示文案参数
     */
    private Object[] args;

    public ServerBizException(String errorCode, String message, Object... args) {
        super(errorCode);
        setDefaultMessage(message);
        setArgs(args);
    }

    public ServerBizException(String errorCode, Object... args) {
        super(errorCode);
        setArgs(args);
    }
}
