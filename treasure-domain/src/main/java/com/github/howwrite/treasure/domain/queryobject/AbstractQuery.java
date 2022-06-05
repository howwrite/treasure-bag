package com.github.howwrite.treasure.domain.queryobject;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public abstract class AbstractQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 4407877253320192069L;
    private Integer pageNo;
    private Integer pageSize;

    public Integer getOffset() {
        Integer no = getPageNo();
        Integer size = getPageSize();
        if (no == null || size == null) {
            return null;
        }
        return (no - 1) * size;
    }

    public Integer getLimit() {
        return getPageSize();
    }
}
