package com.github.howwrite.treasure.core;

public interface PageQuery {

    Integer DEFAULT_PAGE_NO = 1;
    Integer DEFAULT_PAGE_SIZE = 10;


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
