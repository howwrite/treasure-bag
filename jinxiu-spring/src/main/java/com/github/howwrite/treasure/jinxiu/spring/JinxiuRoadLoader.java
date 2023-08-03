package com.github.howwrite.treasure.jinxiu.spring;

import com.github.howwrite.treasure.jinxiu.LetUsGo;
import com.github.howwrite.treasure.jinxiu.domain.executor.factory.RoadExecutorFactory;
import com.github.howwrite.treasure.jinxiu.domain.road.Road;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
public class JinxiuRoadLoader {
    @Resource
    private List<Road> roadList;

    @Resource
    private RoadExecutorFactory roadExecutorFactory;

    @PostConstruct
    public void init() {
        if (CollectionUtils.isEmpty(roadList)) {
            return;
        }
        for (Road road : roadList) {
            LetUsGo.registerRoad(road);
        }
        LetUsGo.registerRoadExecutorFactory(roadExecutorFactory);
    }
}
