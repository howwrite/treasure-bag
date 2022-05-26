package com.github.howwrite.treasure.domain.value;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public abstract class AbstractValueObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 4215771799080523976L;
}
