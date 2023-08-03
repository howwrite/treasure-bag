package com.github.howwrite.treasure.jinxiu.spring;

import com.github.howwrite.treasure.jinxiu.domain.executor.SequentialExecutionRoadExecutor;
import com.github.howwrite.treasure.jinxiu.domain.executor.factory.RoadExecutorFactory;
import com.github.howwrite.treasure.jinxiu.domain.node.NodeProvider;
import com.github.howwrite.treasure.jinxiu.domain.road.DefaultRoadFactory;
import com.github.howwrite.treasure.jinxiu.domain.road.RoadFactory;
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
    public RoadFactory roadFactory(NodeProvider nodeProvider) {
        return new DefaultRoadFactory(nodeProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public NodeProvider nodeProvider(ApplicationContext applicationContext) {
        return applicationContext::getBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public RoadExecutorFactory defaultRoadExecutorFactory(SequentialExecutionRoadExecutor sequentialExecutionRoadExecutor) {
        return road -> sequentialExecutionRoadExecutor;
    }

    @Bean
    public SequentialExecutionRoadExecutor sequentialExecutionRoadExecutor() {
        return new SequentialExecutionRoadExecutor();
    }
}
