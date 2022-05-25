package com.github.howwrite.treasure.domain.model;

import com.github.howwrite.treasure.domain.value.AbstractValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractModel extends AbstractValueObject {
}
