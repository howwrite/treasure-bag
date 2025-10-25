package com.github.howwrite.treasure.core.utils;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 反射工具集合, 围绕泛型继承关系与类型解析提供便捷方法.
 */
public final class ClassUtils {

    private ClassUtils() {
    }

    /**
     * 判断 subclass 是否继承或实现了带泛型的 superclass, 并校验泛型参数与父类声明的上界是否兼容。
     */
    public static boolean isSubclassOfGenerics(Class<?> subclass, Class<?> superclass) {
        if (subclass == null || superclass == null) {
            return false;
        }
        if (!superclass.isAssignableFrom(subclass)) {
            return false;
        }

        TypeVariable<? extends Class<?>>[] typeParameters = superclass.getTypeParameters();
        if (typeParameters.length == 0) {
            // 父类不声明泛型，原始继承关系即可
            return true;
        }

        ParameterizedType parameterizedType = resolveParameterizedType(subclass, superclass);
        if (parameterizedType == null) {
            return false;
        }

        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length != typeParameters.length) {
            return false;
        }

        for (int i = 0; i < typeParameters.length; i++) {
            if (!isTypeCompatible(typeParameters[i], actualTypeArguments[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取 sourceClass 在继承或实现 targetClass 时的第 index 个泛型参数, 解析失败返回 Object。
     */
    public static Class<?> resolveGenericTypeArgument(Class<?> sourceClass, Class<?> targetClass, int index) {
        ParameterizedType parameterizedType = resolveParameterizedType(sourceClass, targetClass);
        if (parameterizedType == null) {
            return Object.class;
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (index < 0 || index >= actualTypeArguments.length) {
            throw new IllegalArgumentException("index 超出泛型参数数量");
        }
        return toClass(actualTypeArguments[index]);
    }

    /**
     * 获取 sourceClass 对 targetClass 的首个泛型参数。
     */
    public static Class<?> resolveGenericTypeArgument(Class<?> sourceClass, Class<?> targetClass) {
        return resolveGenericTypeArgument(sourceClass, targetClass, 0);
    }

    /**
     * 定位 sourceClass 在继承关系链上与 targetClass 对应的参数化类型。
     */
    public static ParameterizedType resolveParameterizedType(Class<?> sourceClass, Class<?> targetClass) {
        if (sourceClass == null || targetClass == null) {
            return null;
        }
        if (!targetClass.isAssignableFrom(sourceClass)) {
            return null;
        }
        return searchParameterizedType(sourceClass, targetClass, new HashSet<>());
    }

    private static ParameterizedType searchParameterizedType(Class<?> currentClass, Class<?> targetClass, Set<Type> visited) {
        if (currentClass == null || currentClass == Object.class) {
            return null;
        }

        Type genericSuperclass = currentClass.getGenericSuperclass();
        ParameterizedType match = matchType(genericSuperclass, targetClass, visited);
        if (match != null) {
            return match;
        }

        for (Type genericInterface : currentClass.getGenericInterfaces()) {
            match = matchType(genericInterface, targetClass, visited);
            if (match != null) {
                return match;
            }
        }
        return searchParameterizedType(currentClass.getSuperclass(), targetClass, visited);
    }

    private static ParameterizedType matchType(Type type, Class<?> targetClass, Set<Type> visited) {
        if (type == null || !visited.add(type)) {
            return null;
        }
        if (type instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class<?> rawClass) {
                if (Objects.equals(rawClass, targetClass)) {
                    return parameterizedType;
                }
                if (targetClass.isAssignableFrom(rawClass)) {
                    return searchParameterizedType(rawClass, targetClass, visited);
                }
            }
        } else if (type instanceof Class<?> rawClass) {
            if (Objects.equals(rawClass, targetClass)) {
                return null;
            }
            if (targetClass.isAssignableFrom(rawClass)) {
                return searchParameterizedType(rawClass, targetClass, visited);
            }
        }
        return null;
    }

    /**
     * 判断实际类型是否满足父类类型变量的约束。
     */
    private static boolean isTypeCompatible(Type expected, Type actual) {
        if (expected instanceof TypeVariable<?> typeVariable) {
            for (Type bound : typeVariable.getBounds()) {
                if (!isTypeCompatible(bound, actual)) {
                    return false;
                }
            }
            return true;
        }
        if (expected instanceof WildcardType wildcardType) {
            for (Type upperBound : wildcardType.getUpperBounds()) {
                if (!isTypeCompatible(upperBound, actual)) {
                    return false;
                }
            }
            for (Type lowerBound : wildcardType.getLowerBounds()) {
                if (!isTypeCompatible(actual, lowerBound)) {
                    return false;
                }
            }
            return true;
        }
        Class<?> expectedClass = toClass(expected);
        Class<?> actualClass = toClass(actual);
        return expectedClass.isAssignableFrom(actualClass);
    }

    /**
     * 将 Type 折算为 Class, 尽可能保留元素类型。
     */
    private static Class<?> toClass(Type type) {
        if (type instanceof Class<?> clazz) {
            return clazz;
        }
        if (type instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class<?> rawClass) {
                return rawClass;
            }
        }
        if (type instanceof GenericArrayType genericArrayType) {
            Class<?> component = toClass(genericArrayType.getGenericComponentType());
            return Array.newInstance(component, 0).getClass();
        }
        if (type instanceof TypeVariable<?> typeVariable) {
            Type[] bounds = typeVariable.getBounds();
            return bounds.length == 0 ? Object.class : toClass(bounds[0]);
        }
        if (type instanceof WildcardType wildcardType) {
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (upperBounds.length > 0) {
                return toClass(upperBounds[0]);
            }
            Type[] lowerBounds = wildcardType.getLowerBounds();
            if (lowerBounds.length > 0) {
                return toClass(lowerBounds[0]);
            }
        }
        return Object.class;
    }
}
