package com.github.howwrite.treasure.core;

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
public class ServerBizException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1861119396443594724L;
    /**
     * 错误信息key
     */
    private String errorKey;
    /**
     * 提示文案参数
     */
    private Object[] args;

    public ServerBizException(String errorKey, Object... args) {
        super(String.format(errorKey, args));
        this.errorKey = errorKey;
        this.args = args;
    }

    public ServerBizException(Throwable throwable, String errorKey, Object... args) {
        super(String.format(errorKey, args), throwable);
        this.errorKey = errorKey;
        this.args = args;
    }

    public ServerBizException(Throwable cause) {
        super(cause);
    }
}
