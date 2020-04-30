package com.nature.edu.feign.hystric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nature.edu.feign.OrderFeignClient;

@Component
public class OrderHystric implements OrderFeignClient {
	private static final Logger logger = LoggerFactory.getLogger(OrderHystric.class);

	@Override
	public boolean create(String userId, String commodityCode, Integer count) {
		logger.warn("订单服务调用异常，这里是订单的熔断处理方法");
		return false;
	}


}
