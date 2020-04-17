package com.nature.edu.config;

import com.nature.edu.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * webmvc配置
 *
 * @author wangck
 * @date 2019/7/16
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 日志拦截器
     */
    @Autowired
    private LogInterceptor logInterceptor;

    @Bean
    LogInterceptor getLogInterceptor() {
        return new LogInterceptor();
    }


    /**
     * 重写添加拦截器方法并添加配置拦截器
     *
     * @param registry
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePatternsList = new ArrayList<>();
        // 静态资源放开不拦截
        excludePatternsList.add("/static/**");
        excludePatternsList.add("/favicon.ico");
        excludePatternsList.add("*.css");
        excludePatternsList.add("*.js");
        excludePatternsList.add("*.jpg");
        excludePatternsList.add("*.png");
        // 日志拦截器
        registry.addInterceptor(logInterceptor).addPathPatterns("/**").excludePathPatterns(excludePatternsList);

    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
    }

    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
