package com.nature.edu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * 
 * @Title: MyBatisPlusConfig.java
 * @Description: MyBatisPlus 配置文件
 * @author lilun
 * @date 2020-04-16 09:58:47 
 * @version 1.0
 */
// 扫描我们的 mapper 文件夹
@SuppressWarnings("deprecation")
@MapperScan("com.nature")
@EnableTransactionManagement
@Configuration // 配置类
public class MyBatisPlusConfig {

    

    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return  new PaginationInterceptor();
    }

    // 逻辑删除组件！
	@Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


    /**
     * @Description:  SQL执行效率插件
     * @return
     * @author lilun
     * @date 2020-04-17 02:35:00
     */
	
	/*
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启，保证我们的效率
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100); //ms 设置sql执行的最大时间，如果超过了则不执行
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }
    */

}
