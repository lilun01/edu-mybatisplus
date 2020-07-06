package com.nature.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;
import feign.Retryer;
@Configuration
public class FeignConfigure {
	
	 public static int connectTimeOutMillis = 12000;//超时时间
     public static int readTimeOutMillis = 12000;
     @Bean
     public Request.Options options() {
            return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
     }

		/*
		 * @Bean public Retryer feignRetryer() { return new Retryer.Default(); }
		 */
     
     @Bean
     public Retryer feignRetryer(){
          Retryer retryer = new Retryer.Default(100, 1000, 4);
          return retryer;
     }

}
