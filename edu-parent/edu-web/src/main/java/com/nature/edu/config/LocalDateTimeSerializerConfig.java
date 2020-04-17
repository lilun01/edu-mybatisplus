package com.nature.edu.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * @Title: LocalDateTimeSerializerConfig.java
 * @Description: 全局日期格式化处理，日期统一用 LocalDateTime  
 * @remark: 个性配置使用：字段增加注解 //@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
 * @author lilun
 * @date 2020-04-16 09:57:17 
 * @version 1.0
 */

@Configuration
public class LocalDateTimeSerializerConfig {
	@Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
	private String pattern;

	@Bean
	public LocalDateTimeSerializer localDateTimeDeserializer() {
		return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
	}
}
