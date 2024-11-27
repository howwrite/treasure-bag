package com.github.howwrite.treasure.core;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class AbstractDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2301881368401755059L;
    private Long id;
    private Long deleted;
    private LocalDateTime deletedTime;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
