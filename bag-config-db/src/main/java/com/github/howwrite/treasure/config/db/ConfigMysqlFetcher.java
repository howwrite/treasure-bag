package com.github.howwrite.treasure.config.db;

import com.github.howwrite.mybatis.dr.starter.DrRepository;
import com.github.howwrite.treasure.config.factory.ConfigFactoryProvider;
import com.github.howwrite.treasure.config.factory.SampleCacheConfigFactory;
import com.github.howwrite.treasure.config.fetcher.ConfigFetcher;
import com.github.howwrite.treasure.config.fetcher.ConfigFetcherProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class ConfigMysqlFetcher implements ConfigFetcher {

    @PostConstruct
    public void init() {
        ConfigFetcherProvider.setProvider((namespace, key) -> this);
        ConfigFactoryProvider.setProvider(new SampleCacheConfigFactory());
    }

    @Override
    public String readConfig(String namespace, String key) {
        ConfigQuery query = new ConfigQuery();
        query.eqNamespace(namespace);
        query.eqKey(key);
        List<Config> list = DrRepository.findByCondition(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0).getContent();
    }
}

