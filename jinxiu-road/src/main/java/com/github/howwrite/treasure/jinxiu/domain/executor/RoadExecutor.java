package com.github.howwrite.treasure.jinxiu.domain.executor;

import com.github.howwrite.treasure.jinxiu.domain.car.Car;
import com.github.howwrite.treasure.jinxiu.domain.road.Road;

import javax.annotation.Nonnull;

public interface RoadExecutor {
    void go(@Nonnull Road road, @Nonnull Car car);
}
