package com.github.howwrite.treasure.core;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractModel<ID> implements Serializable {
    private ID id;
}
