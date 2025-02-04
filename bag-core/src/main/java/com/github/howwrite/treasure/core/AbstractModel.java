package com.github.howwrite.treasure.core;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class AbstractModel<ID> implements Serializable {
    private ID id;
}
