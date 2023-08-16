package com.github.howwrite.treasure.jinxiu;

import com.github.howwrite.treasure.core.ServerBizException;
import com.github.howwrite.treasure.jinxiu.domain.context.ResultContext;
import com.github.howwrite.treasure.jinxiu.domain.executor.PipelineExecutor;
import com.github.howwrite.treasure.jinxiu.domain.executor.factory.PipelineExecutorFactory;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.Pipeline;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LetUsGo {

    private static final Map<String, Pipeline> PIPELINE_MAP_BY_NAME = new ConcurrentHashMap<>();
    private static PipelineExecutorFactory pipelineExecutorFactory;

    public static void registerPipelineExecutorFactory(PipelineExecutorFactory pipelineExecutorFactory) {
        if (LetUsGo.pipelineExecutorFactory != null) {
            throw new ServerBizException("pipelineExecutorFactory already registered, className:%s", LetUsGo.pipelineExecutorFactory.getClass());
        }
        LetUsGo.pipelineExecutorFactory = Objects.requireNonNull(pipelineExecutorFactory);
    }

    public static <Response> Response run(String pipelineName, ResultContext<Response> resultContext) {
        Pipeline pipeline = findPipelineByName(pipelineName);
        if (pipeline == null) {
            throw new ServerBizException("pipeline not found");
        }
        PipelineExecutor pipelineExecutor = findPipelineExecutor(pipeline);
        if (pipelineExecutor == null) {
            throw new ServerBizException("pipelineExecutor not found");
        }
        pipelineExecutor.go(pipeline, resultContext);
        return resultContext.buildResponse();
    }

    @Nullable
    private static Pipeline findPipelineByName(String pipelineName) {
        return PIPELINE_MAP_BY_NAME.get(pipelineName);
    }

    public static void registerPipeline(Pipeline pipeline) {
        if (pipeline == null) {
            throw new ServerBizException("pipeline is null");
        }
        String pipelineName = pipeline.getName();
        if (PIPELINE_MAP_BY_NAME.containsKey(pipelineName))
            throw new ServerBizException("pipeline already registered");
        PIPELINE_MAP_BY_NAME.put(pipelineName, pipeline);
    }

    @Nullable
    public static PipelineExecutor findPipelineExecutor(@Nonnull Pipeline pipeline) {
        return Optional.ofNullable(pipelineExecutorFactory).map(it -> it.getPipelineExecutor(pipeline)).orElse(null);
    }
}
