package com.github.howwrite.treasure.api.response;

import java.io.Serial;
import java.io.Serializable;

/**
 * 不希望子类直接透出id，但是我不知道怎么可以在父类把子类的id属性屏蔽了。。。
 * 那还是看看这行注释别透出id了。不为啥，就是不想透出。
 *
 * @author howwrite
 * @date 2020/10/7 12:20 上午
 */
public class AbstractDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 784964219228539790L;
}
