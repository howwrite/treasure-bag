package com.github.howwrite.treasure.jinxiu.domain.pipeline;

import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import com.github.howwrite.treasure.jinxiu.domain.node.NodeProvider;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultPipelineFactory implements PipelineFactory {
    private final NodeProvider nodeProvider;

    @Override
    public Pipeline genPipelineByNodeTypes(String name, Class<? extends Node>... nodeTypes) {
        if (ArrayUtils.isEmpty(nodeTypes)) {
            throw new ServerBizException("nodeTypes is empty");
        }
        List<Node> nodeList = new ArrayList<>();
        for (Class<? extends Node> nodeType : nodeTypes) {
            nodeList.add(nodeProvider.findNodeByType(nodeType));
        }
        return new Pipeline(name, nodeList);
    }
}
