package com.github.howwrite.treasure.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 业务异常，会透出给前端异常信息
 *
 * @author howwrite
 * @date 2020/10/7 9:47 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServerBizException extends ServerException {
    @Serial
    private static final long serialVersionUID = 1861119396443594724L;
    /**
     * 提示文案参数
     */
    private Object[] args;

    public ServerBizException(String message, Object... args) {
        super(message);
        setArgs(args);
    }
}
