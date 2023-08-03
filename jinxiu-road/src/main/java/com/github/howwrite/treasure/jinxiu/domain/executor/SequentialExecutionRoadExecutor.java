package com.github.howwrite.treasure.jinxiu.domain.executor;

import com.github.howwrite.treasure.jinxiu.domain.car.Car;
import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import com.github.howwrite.treasure.jinxiu.domain.road.Road;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;


@EqualsAndHashCode(callSuper = true)
@Data
public class SequentialExecutionRoadExecutor extends BaseRoadExecutor {
    @Override
    public void go(@Nonnull Road road, @Nonnull Car car) {
        for (Node node : road.getNodeList()) {
            executeRun(node, car);
        }
    }
}
