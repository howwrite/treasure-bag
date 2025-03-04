package com.github.howwrite.treasure.core;

public interface PageQuery {

    default Integer getOffset() {
        Integer no = getPageNo();
        Integer size = getPageSize();
        if (no == null || size == null) {
            return null;
        }
        return (no - 1) * size;
    }

    Integer getPageNo();

    default Integer getLimit() {
        return getPageSize();
    }

    Integer getPageSize();
}
