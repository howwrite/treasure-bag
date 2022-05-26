package com.github.howwrite.treasure.domain.model;

import com.github.howwrite.treasure.domain.value.AbstractValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractModel<ID extends Serializable> extends AbstractValueObject {
    @Serial
    private static final long serialVersionUID = 2714074658002977618L;
    private ID id;
}
