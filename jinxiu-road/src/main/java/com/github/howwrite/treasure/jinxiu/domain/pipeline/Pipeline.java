package com.github.howwrite.treasure.jinxiu.domain.pipeline;

import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import lombok.Data;

import java.util.List;

@Data
public class Pipeline {
    private final String name;
    private final List<Node> nodeList;
}