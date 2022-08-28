package com.github.howwrite.treasure.api.request;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author howwrite
 * @date 2020/10/7 12:20 上午
 */
public abstract class AbstractRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 784964219228539790L;

    public void checkParam() {
    }
}
