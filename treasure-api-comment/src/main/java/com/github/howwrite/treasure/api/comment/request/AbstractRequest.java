package com.github.howwrite.treasure.api.comment.request;

import com.github.howwrite.treasure.api.comment.constant.CommentErrorMap;
import com.github.howwrite.treasure.api.comment.constant.OperationType;
import com.github.howwrite.treasure.comment.util.ParameterUtils;
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
        ParameterUtils.notNull(callOperationType(), CommentErrorMap.OPERATION_TYPE_CAN_NOT_BE_NULL);
    }
}
