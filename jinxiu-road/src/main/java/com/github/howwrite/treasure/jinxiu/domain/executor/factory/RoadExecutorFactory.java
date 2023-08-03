package com.github.howwrite.treasure.jinxiu.domain.executor.factory;

import com.github.howwrite.treasure.jinxiu.domain.executor.RoadExecutor;
import com.github.howwrite.treasure.jinxiu.domain.road.Road;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface RoadExecutorFactory {
    @Nonnull
    RoadExecutor getRoadExecutor(Road road);
}
