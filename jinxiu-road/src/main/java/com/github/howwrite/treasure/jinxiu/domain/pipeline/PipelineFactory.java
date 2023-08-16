package com.github.howwrite.treasure.jinxiu.domain.pipeline;

import com.github.howwrite.treasure.jinxiu.domain.node.Node;

public interface PipelineFactory {
    Pipeline genPipelineByNodeTypes(String name, Class<? extends Node>... nodeTypes);
}
