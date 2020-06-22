package com.nature.edu.config.redis.strategy;


import org.redisson.config.Config;
import com.nature.edu.entity.RedissonProperties;

/**
 * @Title: RedissonConfigService.java
 * @Description: Redisson配置构建接口
 * @author lilun
 * @date 2020-06-16 06:53:02
 * @version 1.0
 */
public interface RedissonConfigService {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     * @param redissonProperties
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
