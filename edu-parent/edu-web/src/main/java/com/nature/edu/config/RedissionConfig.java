package com.nature.edu.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 
 * @Title: RedissionConfig.java
 * @Description: redis链接配置
 * @author lilun
 * @date 2020-04-22 03:19:32 
 * @version 1.0
 */
@Configuration
public class RedissionConfig {
	private static final Logger logger = LoggerFactory.getLogger(RedissionConfig.class);
	
	
	 @Bean
	 public RedissonClient getRedisson(){
	      Config config=new Config();
	      config.useSingleServer().setAddress("redis://127.0.0.1:6379");
	      RedissonClient redisson=Redisson.create(config);
	      logger.info("成功连接Redis Server" );
	      return redisson;
	   }
	  

}
