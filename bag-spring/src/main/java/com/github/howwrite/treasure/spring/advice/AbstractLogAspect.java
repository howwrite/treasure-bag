package com.github.howwrite.treasure.spring.advice;

import com.alibaba.fastjson.JSONObject;
import com.github.howwrite.treasure.core.CheckSelf;
import com.github.howwrite.treasure.core.Response;
import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.core.utils.SplitterUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.util.StopWatch;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * @author howwrite
 * @date 2020/10/7 9:55 下午
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractLogAspect {
    private final MessageSource messageSource;

    /**
     * aop切点
     * 例如: @Pointcut(value = "execution(public * com.github.howwrite.server.api.facade..*(..))")
     */
    public abstract void pointcut();

    /**
     * 是否需要输出请求成功的接口日志
     *
     * @return true: 默认打印请求成功的接口日志
     */
    protected boolean printSuccessLogAble() {
        return true;
    }

    protected Locale getLocale() {
        return Locale.getDefault();
    }

    protected Logger getLogger() {
        return log;
    }

    /**
     * 不同信息之间的连接符
     *
     * @return 默认回车连接
     */
    protected String joiner() {
        return "\n";
    }

    @Around(value = "pointcut()")
    protected Object doApi(ProceedingJoinPoint joinPoint) {
        StopWatch watch = new StopWatch();
        watch.start();
        Object[] args = joinPoint.getArgs();
        try {
            if (args != null) {
                for (Object arg : args) {
                    checkParam(arg);
                }
            }
            Object response = joinPoint.proceed();
            watch.stop();
            if (printSuccessLogAble()) {
                getLogger().info(generateSuccessLog(watch, joinPoint, args, response));
            }
            return response;
        } catch (ServerBizException e) {
            getLogger().warn(referenceLog(joinPoint, args, watch), e);
            final String message = messageSource.getMessage(e.getErrorKey(), e.getArgs(), e.getMessage(), getLocale());
            return Response.fail(message, e.getMessage(), e.getArgs());
        } catch (IllegalArgumentException e) {
            getLogger().warn(referenceLog(joinPoint, args, watch), e);
            final String errorMessage = e.getMessage();
            final String message = messageSource.getMessage(errorMessage, null, errorMessage, getLocale());
            return Response.fail(message, errorMessage);
        } catch (Throwable e) {
            getLogger().error(referenceLog(joinPoint, args, watch), e);
            final String message = messageSource.getMessage("系统开小差啦", null, "系统开小差啦", getLocale());
            return Response.fail(message, "系统开小差啦");
        }
    }

    private void checkParam(Object arg) {
        if (arg instanceof CheckSelf request) {
            request.checkParam();
        }
        if (arg instanceof Iterable<?> iterable) {
            iterable.forEach(this::checkParam);
        }
        if (arg instanceof Collection<?> collection) {
            collection.forEach(this::checkParam);
        }
    }


    /**
     * 记录成功调用日志
     *
     * @param watch    方法执行时长
     * @param point    切面信息
     * @param response 方法返回值
     */
    protected String generateSuccessLog(StopWatch watch, ProceedingJoinPoint point, Object[] request, Object response) {
        return referenceLog(point, request, watch) + handlerReturnObj(response);
    }

    /**
     * 记录切面信息日志
     *
     * @param point     切面信息
     * @param stopwatch 计时器
     * @return 切面信息字符串
     */
    protected String referenceLog(ProceedingJoinPoint point, Object[] request, StopWatch stopwatch) {
        if (stopwatch.isRunning()) {
            stopwatch.stop();
        }
        StringBuilder sb = new StringBuilder();
        Signature pointSignature = point.getSignature();
        sb.append(handlerMethodSign(pointSignature.toString()));
        sb.append(joiner()).append("request: ").append(objectToJsonString(request));
        sb.append(joiner()).append("costTimeMills:").append(stopwatch.getTotalTimeMillis()).append("ms");
        return sb.toString();
    }

    /**
     * 将对象转换成json字符串
     *
     * @param objects 参数列表
     * @return json字符串
     */
    protected String objectToJsonString(Object... objects) {
        if (objects == null) {
            return "null";
        }
        if (objects.length == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder();
        for (Object o : objects) {
            try {
                result.append(JSONObject.toJSONString(o)).append('|');
            } catch (Throwable e) {
                getLogger().warn("object parse to json error, object:{}", o);
                result.append(o.toString()).append('|');
            }
        }
        return result.toString();
    }

    /**
     * 处理返回值
     * data @param response 返回值
     *
     * @return 处理后的字符串
     */
    protected String handlerReturnObj(Object data) {
        return joiner() + "response: " +
                objectToJsonString(data);
    }

    /**
     * 处理方法签名,将长签名改为短签名
     * 例如: c.g.h.t.s.c.a.AbstractServerAspect.handlerMethodSign
     *
     * @param methodSign 方法签名
     * @return 处理过后的方法签名
     */
    protected String handlerMethodSign(String methodSign) {
        StringBuilder result = new StringBuilder();
        List<String> classNames = SplitterUtils.DOT.splitToList(methodSign);
        for (int i = 0, n = classNames.size(); i < n; i++) {
            if (i != 0) {
                result.append('.');
            }
            if (i != 0 && (i + 2) < n) {
                result.append(classNames.get(i).charAt(0));
            } else {
                result.append(classNames.get(i));
            }
        }
        return result.toString();
    }
}
