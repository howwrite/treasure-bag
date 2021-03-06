package com.github.howwrite.treasure.domain.comment;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author howwrite
 * @date 2020/10/8 12:27 上午
 */
@Data
public abstract class AbstractDomain implements Serializable {
    private static final long serialVersionUID = -2301881368401755059L;

    /**
     * 是否删除
     * 0为未删除，否则表示删除
     */
    private Long deleted;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;

}
