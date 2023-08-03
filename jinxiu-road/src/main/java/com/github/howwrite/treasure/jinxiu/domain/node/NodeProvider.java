package com.github.howwrite.treasure.jinxiu.domain.node;

@FunctionalInterface
public interface NodeProvider {
    <T extends Node> T findNodeByType(Class<T> nodeName);
}
