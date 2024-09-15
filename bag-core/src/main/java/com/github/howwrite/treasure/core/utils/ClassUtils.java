package com.github.howwrite.treasure.core.utils;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @author howwrite
 */
public class ClassUtils {
    public static boolean isSubclassOfGenerics(Class<?> subclass, Class<?> superclass) {
        // 检查是否是直接的继承关系
        if (superclass.isAssignableFrom(subclass)) {
            // 获取实际的类型参数
            Type superClassType = superclass.getGenericSuperclass();
            Type subClassType = subclass.getGenericSuperclass();

            // 如果父类或子类不是参数化的类型，则直接返回true
            if (!(superClassType instanceof ParameterizedType superType) || !(subClassType instanceof ParameterizedType subType)) {
                return true;
            }

            // 转换为参数化类型以比较类型参数

            // 获取并比较类型参数
            Type[] superTypeArgs = superType.getActualTypeArguments();
            Type[] subTypeArgs = subType.getActualTypeArguments();

            // 如果泛型参数数量不匹配，则不是正确的子类
            if (superTypeArgs.length != subTypeArgs.length) {
                return false;
            }

            // 比较每个泛型参数
            for (int i = 0; i < superTypeArgs.length; i++) {
                if (!typeEquals(superTypeArgs[i], subTypeArgs[i])) {
                    // 如果泛型参数不匹配，则不是正确的子类
                    return false;
                }
            }
            // 所有泛型参数都匹配，是正确的子类
            return true;
        }
        return false;
    }

    private static boolean typeEquals(Type type1, Type type2) {
        if (type1 instanceof Class && type2 instanceof Class) {
            return ((Class<?>) type1).isAssignableFrom((Class<?>) type2);
        } else if (type1 instanceof ParameterizedType pType1 && type2 instanceof ParameterizedType pType2) {
            return typeEquals(pType1.getRawType(), pType2.getRawType()) &&
                    Arrays.equals(pType1.getActualTypeArguments(), pType2.getActualTypeArguments());
        } else if (type1 instanceof GenericArrayType gType1 && type2 instanceof GenericArrayType gType2) {
            return typeEquals(gType1.getGenericComponentType(), gType2.getGenericComponentType());
        } else if (type1 instanceof TypeVariable<?> tType1 && type2 instanceof TypeVariable<?> tType2) {
            return tType1.equals(tType2);
        } else if (type1 instanceof WildcardType wType1 && type2 instanceof WildcardType wType2) {
            return Arrays.equals(wType1.getUpperBounds(), wType2.getUpperBounds()) &&
                    Arrays.equals(wType1.getLowerBounds(), wType2.getLowerBounds());
        }
        return false;
    }
}
