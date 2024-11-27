package com.github.howwrite.treasure.core;

import lombok.Data;

@Data
public abstract class AbstractModel<ID> {
    private ID id;
}
