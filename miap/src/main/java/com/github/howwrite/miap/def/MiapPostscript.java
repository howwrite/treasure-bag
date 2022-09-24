package com.github.howwrite.miap.def;

/**
 * 书看到了最后一章，结果只是附笔，重要的是过程
 *
 * @author howwrite
 */
@FunctionalInterface
public interface MiapPostscript<Postscript> {
    /**
     * 获取返回值
     *
     * @return 流程结束的返回值
     */
    Postscript seeTheLast();
}
