package com.nature.edu.config;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid过滤器
 * @author wangck
 * @date 2019/8/6
 */
@WebFilter(
		filterName = "druidWebStatFilter", 
		urlPatterns = { "/*" }, 
		initParams = {
		@WebInitParam(
				name = "exclusions", 
				value = "*.js,*.jpg,*.png,*.gif,*.ico,*.css,/druid/*")
})
public class DruidStatFilter extends WebStatFilter {

}
