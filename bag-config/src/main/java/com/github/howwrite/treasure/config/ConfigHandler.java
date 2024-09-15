package com.github.howwrite.treasure.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

@Slf4j
public class ConfigHandler {
    public static void handler(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        ConfigNamespace nameSpace = clazz.getAnnotation(ConfigNamespace.class);
        String prefix = Optional.ofNullable(nameSpace).map(ConfigNamespace::prefix).orElse(null);

        for (Field field : fields) {
            // 检查字段是否被@MyAnnotation注解标记
            if (field.isAnnotationPresent(ConfigGenerator.class)) {
                // 确保字段是静态的
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && field.getType() == Config.class) {
                    try {
                        // 设置私有访问权限
                        field.setAccessible(true);
                        Config<?> currentValue = (Config<?>) field.get(null);
                        if (currentValue == null) {
                            currentValue = ConfigHelper.newConfigInstance();
                        }
                        currentValue.prefix(prefix);

                        ConfigGenerator annotation = field.getAnnotation(ConfigGenerator.class);
                        String key = StringUtils.isBlank(annotation.key()) ? field.getName() : annotation.key();
                        currentValue.key(key);
                        Type genericType = field.getGenericType();
                        if (genericType instanceof ParameterizedType parameterizedType) {
                            currentValue.type(parameterizedType.getActualTypeArguments()[0]);
                        }

                        field.set(null, currentValue);
                    } catch (IllegalAccessException e) {
                        log.error("handler config error", e);
                    }
                }
            }
        }
    }
}
