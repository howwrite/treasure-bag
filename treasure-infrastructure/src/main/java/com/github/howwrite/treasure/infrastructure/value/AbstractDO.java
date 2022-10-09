package com.github.howwrite.treasure.infrastructure.value;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class AbstractDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2301881368401755059L;

    /**
     * id
     */
    private Long id;
    /**
     * 是否删除
     * 0为未删除，否则表示删除
     */
    private Long deleted;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

    /**
     * 记录创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 记录更新时间
     */
    private LocalDateTime updatedTime;

}