package com.github.howwrite.treasure.server.aop;

import com.alibaba.fastjson.JSONObject;
import com.github.howwrite.treasure.api.constant.OperationType;
import com.github.howwrite.treasure.api.request.AbstractRequest;
import com.github.howwrite.treasure.api.response.Response;
import com.github.howwrite.treasure.server.exception.ServerBizException;
import com.github.howwrite.treasure.util.SplitterUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.springframework.context.MessageSource;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Locale;

/**
 * @author howwrite
 * @date 2020/10/7 9:55 下午
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractServerAspect {
    private final MessageSource messageSource;

    /**
     * aop切点
     * 例如: @Pointcut(value = "execution(public * com.github.howwrite.server.api.facade..*(..))")
     */
    public abstract void apiPointcut();

    /**
     * 是否需要输出请求成功的接口日志
     *
     * @return true: 默认打印请求成功的接口日志
     */
    protected boolean printSuccessLogAble() {
        return true;
    }

    /**
     * 不同信息之间的连接符
     *
     * @return 默认回车连接
     */
    protected String joiner() {
        return "\n";
    }

    @Around(value = "apiPointcut()")
    protected Response<?> doApi(ProceedingJoinPoint joinPoint) {
        StopWatch watch = new StopWatch();
        watch.start();
        final Object arg = joinPoint.getArgs()[0];
        // 单参原则：只能有一个参数，并且继承自AbstractRequest
        final AbstractRequest request = (AbstractRequest) arg;
        try {
            request.checkParam();
            Response<?> response = (Response<?>) joinPoint.proceed();
            watch.stop();
            if (printSuccessLogAble()) {
                log.info(generateSuccessLog(watch, joinPoint, request, response));
            }
            return response;
        } catch (ServerBizException e) {
            log.warn(referenceLog(joinPoint, request, watch), e);
            final String message = messageSource.getMessage(e.getMessage(), e.getArgs(), e.getMessage(), Locale.getDefault());
            return Response.fail(message, e.getMessage(), e.getArgs());
        } catch (IllegalArgumentException e) {
            log.warn(referenceLog(joinPoint, request, watch), e);
            final String errorMessage = e.getMessage();
            final String message = messageSource.getMessage(errorMessage, null,errorMessage, Locale.getDefault());
            return Response.fail(message, errorMessage);
        } catch (Throwable e) {
            log.error(referenceLog(joinPoint, request, watch), e);
            final String message = messageSource.getMessage("系统开小差啦", null,"系统开小差啦", Locale.getDefault());
            return Response.fail(message, "系统开小差啦");
        }
    }


    /**
     * 记录成功调用日志
     *
     * @param watch    方法执行时长
     * @param point    切面信息
     * @param response 方法返回值
     */
    protected String generateSuccessLog(StopWatch watch, ProceedingJoinPoint point, AbstractRequest request, Response<?> response) {
        return referenceLog(point, request, watch) + handlerReturnObj(response);
    }

    /**
     * 记录切面信息日志
     *
     * @param point     切面信息
     * @param stopwatch 计时器
     * @return 切面信息字符串
     */
    protected String referenceLog(ProceedingJoinPoint point, AbstractRequest request, StopWatch stopwatch) {
        if (stopwatch.isRunning()) {
            stopwatch.stop();
        }
        StringBuilder sb = new StringBuilder();
        Signature pointSignature = point.getSignature();
        sb.append(handlerMethodSign(pointSignature.toString()));
        sb.append(joiner()).append("operatorType: ").append(handlerOperatorType(request.callOperationType()));
        sb.append(joiner()).append("request: ").append(objectToJsonString(request));
        sb.append(joiner()).append("costTimeMills:").append(stopwatch.getTotalTimeMillis()).append("ms");
        return sb.toString();
    }

    /**
     * 处理操作类型
     *
     * @param type 处理操作类型
     * @return 操作类型中的信息
     */
    protected String handlerOperatorType(OperationType type) {
        StringBuilder sb = new StringBuilder("isWrite:").append(type.isWrite());
        sb.append("  Introduction:").append(type.getIntroduction());
        return sb.toString();
    }

    /**
     * 将对象转换成json字符串
     *
     * @param o 对象
     * @return json字符串
     */
    protected String objectToJsonString(Object o) {
        try {
            return JSONObject.toJSONString(o);
        } catch (Throwable e) {
            log.warn("object parse to json error, object:{}", o);
            return o.toString();
        }
    }

    /**
     * 处理返回值
     *
     * @param response 返回值
     * @return 处理后的字符串
     */
    protected String handlerReturnObj(Response<?> response) {
        StringBuilder result = new StringBuilder(joiner()).append("response: ");
        final Object data = response.getData();
        if (data == null) {
            result.append("null");
        } else {
            result.append(objectToJsonString(data));
        }
        return result.toString();
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
