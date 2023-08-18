package com.github.howwrite.treasure.jinxiu.domain.executor;

import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.jinxiu.domain.context.Context;
import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public abstract class BasePipelineExecutor implements PipelineExecutor {

    private final static Set<String> EXECUTE_METHOD_NAMES = Sets.newHashSet("execute", "e");

    public final void executeRun(@Nonnull Node node, @Nonnull Context context) {
        try {
            Method executeMethod = findExecuteMethod(node.getClass());
            int parameterCount = executeMethod.getParameterCount();
            Object[] parameters = new Object[parameterCount];
            Arrays.fill(parameters, context);
            executeMethod.invoke(node, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            // todo
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public Method findExecuteMethod(@Nonnull Class<?> clazz) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (EXECUTE_METHOD_NAMES.contains(declaredMethod.getName())) {
                return declaredMethod;
            }
        }
        throw new ServerBizException("%s无execute方法", clazz.getName());
    }
}
