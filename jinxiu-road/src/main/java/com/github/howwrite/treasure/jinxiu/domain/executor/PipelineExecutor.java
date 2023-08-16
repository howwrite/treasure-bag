package com.github.howwrite.treasure.jinxiu.domain.executor;

import com.github.howwrite.treasure.jinxiu.domain.context.Context;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.Pipeline;

import javax.annotation.Nonnull;

public interface PipelineExecutor {
    void go(@Nonnull Pipeline pipeline, @Nonnull Context context);
}
