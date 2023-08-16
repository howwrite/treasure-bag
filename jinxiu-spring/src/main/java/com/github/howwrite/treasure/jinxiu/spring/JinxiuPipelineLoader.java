package com.github.howwrite.treasure.jinxiu.spring;

import com.github.howwrite.treasure.jinxiu.LetUsGo;
import com.github.howwrite.treasure.jinxiu.domain.executor.factory.PipelineExecutorFactory;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.Pipeline;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
public class JinxiuPipelineLoader {
    @Resource
    private List<Pipeline> pipelineList;

    @Resource
    private PipelineExecutorFactory pipelineExecutorFactory;

    @PostConstruct
    public void init() {
        if (CollectionUtils.isEmpty(pipelineList)) {
            return;
        }
        for (Pipeline pipeline : pipelineList) {
            LetUsGo.registerPipeline(pipeline);
        }
        LetUsGo.registerPipelineExecutorFactory(pipelineExecutorFactory);
    }
}
