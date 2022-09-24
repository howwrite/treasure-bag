package com.github.howwrite.miap.def;

import java.io.Serial;

/**
 * 一个读者实现类，带着自己的从序章读到后记，包括上下文信息和返回值。
 *
 * @param <Postscript>
 */
public abstract class MiapReader<Postscript> implements MiapPreface, MiapPostscript<Postscript> {
    @Serial
    private static final long serialVersionUID = 2028348891756909067L;

}
