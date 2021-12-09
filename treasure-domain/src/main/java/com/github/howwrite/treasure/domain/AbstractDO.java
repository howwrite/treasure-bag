package com.github.howwrite.treasure.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author howwrite
 * @date 2020/10/8 12:27 上午
 */
@Data
public abstract class AbstractDO<ID> implements Serializable {
    private static final long serialVersionUID = -2301881368401755059L;

    /**
     * id
     */
    private ID            id;
    /**
     * 是否删除
     * 0为未删除，否则表示删除
     */
    private Long          deleted;
    /**
     * 删除时间
     */
    private Long deletedTime;

    /**
     * 记录创建时间
     */
    private Long createdTime;

    /**
     * 记录更新时间
     */
    private Long updatedTime;

}
