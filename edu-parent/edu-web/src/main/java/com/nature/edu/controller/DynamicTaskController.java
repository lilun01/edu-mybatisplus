package com.nature.edu.controller;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nature.edu.service.IUserService;
import com.nature.edu.task.DynamicTask;
import com.nature.edu.util.date.DateCoreUtil;

@RestController
public class DynamicTaskController {
	//private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private RedisLockRegistry redisLockRegistry;
	@Autowired
	private IUserService userService;

	@Autowired
	private DynamicTask dynamicTask;
	
	/**
	 * 
	 * @Title: infoLock
	 * @Description: 分布式锁实现 举例
	 * @param userId
	 * @param lockKey 加锁的关键字，一般使用实体的唯一值 id,code等
	 * @return
	 * @author lilun
	 * @date 2020-04-21 10:42:52
	 */
	@GetMapping("/task/add")
	public String add(@RequestParam String userId, @RequestParam String lockKey,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
		//return null;
		String cornStr = DateCoreUtil.getCron(date);
		System.out.println("cornStr="+cornStr);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		             dynamicTask.startCron(userId,redisLockRegistry, userService,cornStr);
				}
			}).start();
		}
		return "测试完成";
		
	}
}
