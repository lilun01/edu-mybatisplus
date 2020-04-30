package com.nature.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.edu.feign.OrderFeignClient;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderFeignClient orderFeignClient;

	@RequestMapping("/create")
	public boolean create() {
		long startTime = System.currentTimeMillis();
		 boolean result = orderFeignClient.create("U100000", "C100000", 30);
		 long endTime = System.currentTimeMillis();
		 System.out.println("调用结果="+result+",耗时："+(endTime-startTime)/1000f+"s");
		return result;
	}

}
