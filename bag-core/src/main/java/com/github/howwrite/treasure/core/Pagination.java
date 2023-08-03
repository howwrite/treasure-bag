package com.github.howwrite.treasure.core;

import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;

@Data
public class Pagination<T> implements Serializable {
    private List<T> data;
    private Integer total;
    private Integer pageNo;
    private Integer pageSize;

    public Pagination(List<T> data, Integer total, @Nonnull PageParam pageParam) {
        this.data = data;
        this.total = total;
        this.pageNo = pageParam.getPageNo();
        this.pageSize = pageParam.getPageSize();
    }
}
