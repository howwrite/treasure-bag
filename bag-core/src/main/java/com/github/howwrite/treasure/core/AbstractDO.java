package com.github.howwrite.treasure.core;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class AbstractDO implements Serializable {
    private Long id;
    private Long deleted;
    private LocalDateTime deletedTime;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
