package com.github.howwrite.treasure.api.request;

import com.github.howwrite.treasure.api.constant.OperationType;
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
     * 获取当前请求的操作类型信息
     *
     * @return 当前请求的操作类型信息
     */
    @NotNull
    @ApiModelProperty(hidden = true)
    public abstract OperationType callOperationType();

    public void checkParam() {
        ParameterUtils.notNull("操作状态不可以为空", callOperationType());
    }
}
