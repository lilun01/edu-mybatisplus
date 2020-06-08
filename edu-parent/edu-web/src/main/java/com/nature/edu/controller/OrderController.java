package com.nature.edu.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.nature.edu.feign.OrderFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private UserController userController;


    @SentinelResource(value = "create", blockHandler = "execptionHandler")
    @PostMapping("/create")
    public String create() {
        long startTime = System.currentTimeMillis();
        boolean result = orderFeignClient.create("U100000", "C100000", 30);
        long endTime = System.currentTimeMillis();
        float f = (endTime - startTime) / 1000f;
        System.out.println("调用结果=" + result + ",耗时：" + (endTime - startTime) / 1000f + "s");
        return result + ",耗时：" + f;
    }


    @RequestMapping("/create2")
    public String create2() {
        String s = userController.testSentinel(null);
        return s;
    }


}
