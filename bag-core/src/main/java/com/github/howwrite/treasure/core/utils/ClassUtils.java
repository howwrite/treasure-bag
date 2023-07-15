package com.github.howwrite.treasure.core.utils;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author howwrite
 */
public class ClassUtils {
    private static final Map<Class<?>, Set<Class<?>>> FAMILY_CLASS_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 查询类以及类所有的父类和实现的接口集合
     *
     * @param clz 待查询的类
     * @return 该类以及所有父类，包括接口
     */
    public static Set<Class<?>> queryFamilyClass(Class<?> clz) {
        if (clz == null) {
            return Sets.newHashSet();
        }
        Set<Class<?>> cacheResult = FAMILY_CLASS_CACHE_MAP.get(clz);
        if (!CollectionUtils.isEmpty(cacheResult)) {
            return cacheResult;
        }
        Set<Class<?>> result = Sets.newHashSet();
        result.add(clz);
        // todo 补充寻找父类逻辑
        FAMILY_CLASS_CACHE_MAP.put(clz, result);
        return result;
    }
}
