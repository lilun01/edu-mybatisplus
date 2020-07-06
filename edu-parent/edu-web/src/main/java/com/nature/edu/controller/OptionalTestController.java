package com.nature.edu.controller;

import cn.hutool.json.JSONUtil;
import com.nature.edu.entity.BasUser;
import com.nature.edu.entity.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Title: OptionalTestController.java
 * @Description: optional 使用示例
 * @author lilun
 * @date 2020-06-08 10:47:07 
 * @version 1.0
 */
@RestController
@RequestMapping("/optional")
@Slf4j
public class OptionalTestController {
    
    
	/**
	 * @Description: 多层if判断时，使用optional 替换
	 * @return
	 * @author lilun
	 * @date 2020-06-08 10:47:31
	 */
	@RequestMapping("/optionalTest")
	public String optionalTest() {
		BasUser user = new BasUser();
		user.setUserName("lisi");
		UserAddress address = new UserAddress();
		address.setDeleted(0);
		address.setAddress("四川");
		user.setUserAddress(address);
		
		log.info("user={}",JSONUtil.toJsonStr(user));
		//其中一个为空，表示不存在，没有返回默认值
		String name = Optional.ofNullable(user).map(BasUser::getUserAddress).map(UserAddress::getAddress).orElse("没有符合条件的字符串");
		//没有，抛出异常
		//String name2 = Optional.ofNullable(user).map(BasUser::getUserAddress).map(UserAddress::getAddress).orElseThrow(()->{throw new IllegalStateException("没有存在的值");});

		return name;
	}
	
	@RequestMapping("/loopTest")
	public boolean  loopTest() throws InterruptedException {
		log.info("等待2s进入死循环");
		Thread.sleep(2000);
		long  i =1;
		while(true) {
			i = i+1;
		}
	}


}
