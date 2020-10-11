package com.github.howwrite.treasure.api.comment.constant;

/**
 * @author howwrite
 * @date 2020/10/7 9:37 下午
 */
public interface OperationType {
    /**
     * 当前操作类型是否可写
     *
     * @return true: 可写操作
     */
    boolean isWrite();

    /**
     * 当前操作的简介
     *
     * @return 当前操作的简介
     */
    String getIntroduction();
}
