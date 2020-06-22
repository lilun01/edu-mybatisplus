package com.nature.edu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.edu.entity.BasUser;

/**
 * @Title: UserMemoryTestController.java
 * @Description: 测试oom
 * @author lilun
 * @date 2020-04-23 02:07:48
 * @version 1.0
 */
@RestController
@RequestMapping("/memory")
public class UserMemoryTestController {

	private static final Logger logger = LoggerFactory.getLogger(UserMemoryTestController.class);

	/**
	 * @Title: infoLockTest
	 * @Description: 分布式锁
	 * @param userId
	 * @param lockKey
	 * @return
	 * @author lilun
	 * @date 2020-04-21 02:56:50
	 */
	@GetMapping("/test")
	public String loop() {
		logger.info("这个是测试日志{}",3);
		add();
		return "finish";
	}

	public void add() {
		List<BasUser> list = new ArrayList<BasUser>();
		boolean f = true;
		Random random = new Random();
		while (f) {
			BasUser user = new BasUser();
			user.setUserId(random.nextInt(1000000) + "");
			list.add(user);
		}
	}

}
