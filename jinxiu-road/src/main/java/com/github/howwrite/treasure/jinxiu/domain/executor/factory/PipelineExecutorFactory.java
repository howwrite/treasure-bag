package com.github.howwrite.treasure.jinxiu.domain.executor.factory;

import com.github.howwrite.treasure.jinxiu.domain.executor.PipelineExecutor;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.Pipeline;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface PipelineExecutorFactory {
    @Nonnull
    PipelineExecutor getPipelineExecutor(Pipeline pipeline);
}
