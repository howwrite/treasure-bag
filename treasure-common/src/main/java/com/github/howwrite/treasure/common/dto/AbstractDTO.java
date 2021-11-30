package com.github.howwrite.treasure.common.dto;

import java.io.Serializable;

/**
 * @author howwrite
 * @date 2021/11/25
 */
public abstract class AbstractDTO implements Serializable {
    private static final long serialVersionUID = 7873836411756701110L;

    public abstract String toString();
}
