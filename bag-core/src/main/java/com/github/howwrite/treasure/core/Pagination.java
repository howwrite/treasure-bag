package com.github.howwrite.treasure.core;

import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Pagination(List<T> data, Integer total, @Nullable PageQuery pageQuery) {
        this(data, total, Optional.ofNullable(pageQuery).map(PageQuery::getPageNo).orElse(null),
                Optional.ofNullable(pageQuery).map(PageQuery::getPageSize).orElse(null));
    }

    public Pagination(List<T> data, @Nonnull PageQuery pageQuery) {
        this(data, null, pageQuery);
    }

    public static <T> Pagination<T> empty() {
        return new Pagination<>(new ArrayList<>(), 0, null);
    }

    public static <T> List<T> paginateList(List<T> list, int pageNo, int pageSize) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        int startIndex = (pageNo - 1) * pageSize;
        if (startIndex >= list.size()) {
            return new ArrayList<>();
        }
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}
