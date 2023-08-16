package com.github.howwrite.treasure.jinxiu.spring;

import com.github.howwrite.treasure.jinxiu.domain.executor.SequentialExecutionPipelineExecutor;
import com.github.howwrite.treasure.jinxiu.domain.executor.factory.PipelineExecutorFactory;
import com.github.howwrite.treasure.jinxiu.domain.node.NodeProvider;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.DefaultPipelineFactory;
import com.github.howwrite.treasure.jinxiu.domain.pipeline.PipelineFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan
@Configuration
public class JinxiuRoadRegister {
    @Bean
    @ConditionalOnMissingBean
    public PipelineFactory pipelineFactory(NodeProvider nodeProvider) {
        return new DefaultPipelineFactory(nodeProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public NodeProvider nodeProvider(ApplicationContext applicationContext) {
        return applicationContext::getBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public PipelineExecutorFactory defaultPipelineExecutorFactory(SequentialExecutionPipelineExecutor sequentialExecutionPipelineExecutor) {
        return pipeline -> sequentialExecutionPipelineExecutor;
    }

    @Bean
    public SequentialExecutionPipelineExecutor sequentialExecutionPipelineExecutor() {
        return new SequentialExecutionPipelineExecutor();
    }
}
