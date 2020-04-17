package com.nature.edu.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 读取druid连接池配置初始化datasource
 *
 * @author wangck
 * @date 2019/8/6
 */
@Configuration
@ServletComponentScan
@MapperScan("com.nature.edu.dao")
public class DruidConfig {

    /**
     * 加载时读取指定的配置信息,前缀为spring.datasource.druid
     *
     * @return
     */
    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

}
