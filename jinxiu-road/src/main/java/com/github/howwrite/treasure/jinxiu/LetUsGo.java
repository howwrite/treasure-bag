package com.github.howwrite.treasure.jinxiu;

import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.jinxiu.domain.car.Truck;
import com.github.howwrite.treasure.jinxiu.domain.executor.RoadExecutor;
import com.github.howwrite.treasure.jinxiu.domain.executor.factory.RoadExecutorFactory;
import com.github.howwrite.treasure.jinxiu.domain.road.Road;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LetUsGo {

    private static final Map<String, Road> ROAD_MAP = new ConcurrentHashMap<>();
    private static RoadExecutorFactory roadExecutorFactory;

    public static void registerRoadExecutorFactory(RoadExecutorFactory roadExecutorFactory) {
        if (LetUsGo.roadExecutorFactory != null) {
            throw new ServerBizException("RoadExecutorFactory already registered, className:%s", LetUsGo.roadExecutorFactory.getClass());
        }
        LetUsGo.roadExecutorFactory = Objects.requireNonNull(roadExecutorFactory);
    }

    public static <Response> Response run(String roadName, Truck<Response> car) {
        Road road = findNodeByName(roadName);
        if (road == null) {
            throw new ServerBizException("road not found");
        }
        RoadExecutor roadExecutorByRoad = findRoadExecutorByRoad(road);
        if (roadExecutorByRoad == null) {
            throw new ServerBizException("roadExecutor not found");
        }
        roadExecutorByRoad.go(road, car);
        return car.buildResponse();
    }

    @Nullable
    private static Road findNodeByName(String roadName) {
        return ROAD_MAP.get(roadName);
    }

    public static void registerRoad(Road road) {
        if (road == null) {
            throw new ServerBizException("roadName or road is null");
        }
        String roadName = road.getName();
        if (ROAD_MAP.containsKey(roadName))
            throw new ServerBizException("road already registered");
        ROAD_MAP.put(roadName, road);
    }

    @Nullable
    public static RoadExecutor findRoadExecutorByRoad(@Nonnull Road road) {
        return Optional.ofNullable(roadExecutorFactory).map(it -> it.getRoadExecutor(road)).orElse(null);
    }
}
