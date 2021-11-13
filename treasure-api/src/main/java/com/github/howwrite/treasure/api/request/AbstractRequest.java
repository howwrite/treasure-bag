package com.github.howwrite.treasure.api.request;

import com.github.howwrite.treasure.util.ParameterUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author howwrite
 * @date 2020/10/7 12:20 上午
 */
public abstract class AbstractRequest implements Serializable {
    private static final long serialVersionUID = 784964219228539790L;

    /**
     * 获取当前请求的操作简介信息
     *
     * @return 当前请求的操作简介信息
     */
    @NotNull
    @ApiModelProperty(hidden = true)
    public abstract String callIntroduction();

    public void checkParam() {
        ParameterUtils.notNull("接口操作不可为空", callIntroduction());
    }
}
