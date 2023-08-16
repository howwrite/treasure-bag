package com.github.howwrite.treasure.jinxiu.domain.executor;

import com.github.howwrite.treasure.jinxiu.domain.context.Context;
import com.github.howwrite.treasure.jinxiu.domain.node.Node;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.Pipeline;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;


@EqualsAndHashCode(callSuper = true)
@Data
public class SequentialExecutionPipelineExecutor extends BasePipelineExecutor {
    @Override
    public void go(@Nonnull Pipeline pipeline, @Nonnull Context context) {
        for (Node node : pipeline.getNodeList()) {
            executeRun(node, context);
        }
    }
}
