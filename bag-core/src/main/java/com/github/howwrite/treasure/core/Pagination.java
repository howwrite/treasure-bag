package com.github.howwrite.treasure.core;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Pagination<T> implements Serializable {
    private List<T> data;
    private Integer total;
    private Integer pageNo;
    private Integer pageSize;


    public Pagination(List<T> data, Integer total, Integer pageNo, Integer pageSize) {
        this.data = data;
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public static <T> Pagination<T> empty() {
        return new Pagination<>(new ArrayList<>(), 0, 1, 10);
    }
}
