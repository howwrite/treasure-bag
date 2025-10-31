package com.github.howwrite.treasure.config.db;

import com.github.howwrite.annotation.DrColumn;
import com.github.howwrite.annotation.DrTable;
import lombok.Data;

import java.time.LocalDateTime;

@DrTable(value = "config", logicDelete = true)
@Data
public class Config {
    /**
     * id
     */
    @DrColumn(value = "id", whenDuplicateUpdate = false)
    private Long id;

    /**
     * 配置命名空间
     */
    @DrColumn("namespace")
    private String namespace;

    /**
     * 配置key
     */
    @DrColumn("key")
    private String key;

    /**
     * 配置内容
     */
    @DrColumn("content")
    private String content;

    @DrColumn("created_time")
    private LocalDateTime createdTime;

    @DrColumn("updated_time")
    private LocalDateTime updatedTime;

}
