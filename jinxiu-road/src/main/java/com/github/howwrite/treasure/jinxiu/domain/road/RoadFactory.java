package com.github.howwrite.treasure.jinxiu.domain.road;

import com.github.howwrite.treasure.jinxiu.domain.node.Node;

public interface RoadFactory {
    Road genRoadByNodeTypes(String name, Class<? extends Node>... nodeTypes);
}
