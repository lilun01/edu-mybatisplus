package com.nature.edu.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.druid.support.json.JSONUtils;
import com.esotericsoftware.minlog.Log;
import com.nature.edu.entity.BasUser;
import com.nature.edu.entity.UserAddress;
import com.nature.edu.feign.OrderFeignClient;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
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
    
	@RequestMapping("/optionalTest")
	public String optionalTest() {
		BasUser user = new BasUser();
		user.setUserName("lisi");
		
		UserAddress address = new UserAddress();
		address.setDeleted(0);
		user.setUserAddress(address);
		//address.setAddress("四川");
		log.info("user={}",JSONUtil.toJsonStr(user));
		//String name = Optional.ofNullable(user).map(BasUser::getUserAddress).map(UserAddress::getAddress).orElse("没有符合条件的字符串");
		String name2 = Optional.ofNullable(user).map(BasUser::getUserAddress).map(UserAddress::getAddress).orElseThrow(()->{throw new IllegalStateException("没有存在的值");});

		return name2;
	}


}
