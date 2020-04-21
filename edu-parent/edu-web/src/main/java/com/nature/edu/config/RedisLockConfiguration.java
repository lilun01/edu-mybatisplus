package com.nature.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * 
 * @Title: RedisLockConfiguration.java
 * @Description: 分布式锁的配置类
 * @author lilun
 * @date 2020-04-21 10:47:09 
 * @version 1.0
 */
@Configuration
public class RedisLockConfiguration {
	
	  @Bean
	  public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
		 RedisLockRegistry redisLockRegistry = new RedisLockRegistry(redisConnectionFactory, "my-lock",10 * 1000L);
	    return redisLockRegistry;
	  }


}
