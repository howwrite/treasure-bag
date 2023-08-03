package com.github.howwrite.treasure.jinxiu.domain.road;

import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import lombok.Data;

import java.util.List;

@Data
public class Road {
    private final String name;
    private final List<Node> nodeList;
}