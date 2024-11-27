package com.github.howwrite.treasure.core;

import java.io.Serializable;

public abstract class AbstractRequest implements CheckSelf, Serializable {
    @Override
    public void checkParam() {
    }
}
