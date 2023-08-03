package com.github.howwrite.treasure.core;

import lombok.Data;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Pagination<T> implements Serializable {
    private List<T> data;
    private Integer total;
    private Integer pageNo;
    private Integer pageSize;

    public Pagination(List<T> data, Integer total, @Nullable PageQuery pageQuery) {
        this.data = data;
        this.total = total;
        if (pageQuery != null) {
            this.pageNo = pageQuery.getPageNo();
            this.pageSize = pageQuery.getPageSize();
        }
    }

    public static <T> Pagination<T> empty() {
        return new Pagination<>(new ArrayList<>(), 0, null);
    }
}
