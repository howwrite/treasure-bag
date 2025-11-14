package com.github.howwrite.treasure.core;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractQuery implements Serializable, PageQuery {
    private Integer pageNo;
    private Integer pageSize;
}
