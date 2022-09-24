package com.github.howwrite.miap.def;


/**
 * 逻辑执行单元定义，用来写实际逻辑
 */
public interface MiapBook<Context extends MiapPreface> {
    /**
     * 这本书是否可读 这个方法是否可以执行
     *
     * @param context 上下文参数
     * @return 当前方法是否需要执行
     */
    default boolean canRead(MiapPreface context) {
        return true;
    }

    /**
     * 执行逻辑的方法
     *
     * @param context 上下文信息
     */
    void execute(MiapPreface context);
}
