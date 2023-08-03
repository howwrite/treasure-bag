package com.github.howwrite.treasure.jinxiu.domain.executor;

import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.jinxiu.domain.car.Car;
import com.github.howwrite.treasure.jinxiu.domain.node.Node;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class BaseRoadExecutor implements RoadExecutor {
    public final void executeRun(@Nonnull Node node, @Nonnull Car car) {
        try {
            Method executeMethod = findExecuteMethod(node.getClass());
            int parameterCount = executeMethod.getParameterCount();
            Object[] parameters = new Object[parameterCount];
            Arrays.fill(parameters, car);
            executeMethod.invoke(node, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // todo
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public Method findExecuteMethod(@Nonnull Class<?> clazz) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if ("execute".equals(declaredMethod.getName())) {
                return declaredMethod;
            }
        }
        throw new ServerBizException("%s无execute方法", clazz.getName());
    }
}
