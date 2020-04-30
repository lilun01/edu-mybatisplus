package com.nature.edu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nature.edu.feign.hystric.OrderHystric;

/**
 * @Description: 远程调用点单服务
 * @author lilun
 * @date 2020-04-30 02:41:15
 * @version 1.0
 */
@FeignClient(name = "order-service", url = "127.0.0.1:8082", fallback = OrderHystric.class)
//@FeignClient(name = "order-service", url = "127.0.0.1:8082")
public interface OrderFeignClient {

	@GetMapping("/create")
	boolean create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode,
			@RequestParam("count") Integer count);

}
