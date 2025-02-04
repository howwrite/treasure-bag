package com.github.howwrite.treasure.core;

import java.io.Serializable;

public abstract class AbstractQuery implements Serializable {
    private Integer offset;
    private Integer limit;
}
