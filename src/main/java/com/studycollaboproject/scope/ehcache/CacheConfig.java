package com.studycollaboproject.scope.ehcache;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    @Bean
    CacheManager getCacheManager() {

        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfigurationBuilder<String, String> configurationBuilder =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class, String.class,
                                ResourcePoolsBuilder.heap(50)
                                        .offheap(10, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(1)));

        CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(new CacheEventLogger()
                        , EventType.CREATED, EventType.EXPIRED).unordered().asynchronous();

        //create caches we need
        cacheManager.createCache("Post",
                Eh107Configuration.fromEhcacheCacheConfiguration(configurationBuilder.withService(asynchronousListener)));

        return cacheManager;
    }

    @Override
    public org.springframework.cache.CacheManager cacheManager() {
        return null;
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return null;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
