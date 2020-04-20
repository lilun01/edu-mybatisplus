package com.nature.edu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

/**
 * @author wangck
 * @date 2019/8/6
 */
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.nature.edu.service")
public class NatureEduWebApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(NatureEduWebApplication.class);

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NatureEduWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        logger.info("===============开始启动！============");
        SpringApplication.run(NatureEduWebApplication.class, args);
        logger.info("===============启动完成！============");
    }

}