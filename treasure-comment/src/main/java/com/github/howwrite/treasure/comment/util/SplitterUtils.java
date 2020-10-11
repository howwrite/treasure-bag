package com.github.howwrite.treasure.comment.util;

import com.google.common.base.Splitter;

/**
 * @author howwrite
 * @date 2020/10/7 10:50 下午
 */
public class SplitterUtils {
    public static final Splitter DOT = Splitter.on(".").omitEmptyStrings().trimResults();
}
