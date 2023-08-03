package com.github.howwrite.treasure.jinxiu.domain.road;

import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import com.github.howwrite.treasure.jinxiu.domain.node.NodeProvider;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultRoadFactory implements RoadFactory {
    private final NodeProvider nodeProvider;

    @Override
    public Road genRoadByNodeTypes(String name, Class<? extends Node>... nodeTypes) {
        if (ArrayUtils.isEmpty(nodeTypes)) {
            throw new ServerBizException("nodeTypes is empty");
        }
        List<Node> nodeList = new ArrayList<>();
        for (Class<? extends Node> nodeType : nodeTypes) {
            nodeList.add(nodeProvider.findNodeByType(nodeType));
        }
        return new Road(name, nodeList);
    }
}
