package com.nature.edu.task;

import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nature.edu.service.IUserService;


@Component
public class DynamicTask {
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private ScheduledFuture<?> future;
	
	

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}

	/**
	 * 
	 * @Description: 模拟启动定时任务
	 * @param id
	 * @param redisLockRegistry
	 * @param userService
	 * @param cornStr
	 * @return
	 * @author lilun
	 * @date 2020-04-21 06:12:48
	 */
	@RequestMapping("/startCron")
	public String startCron(String id,RedisLockRegistry redisLockRegistry,IUserService userService,String cornStr) {

		future = threadPoolTaskScheduler.schedule(new MyRunnable(id,redisLockRegistry,userService,cornStr), new CronTrigger(cornStr));
		return "startCron";
	}

	@RequestMapping("/stopCron")
	public String stopCron() {

		if (future != null) {
			future.cancel(true);
		}
		System.out.println("DynamicTask.stopCron()");
		return "stopCron";
	}

	@RequestMapping("/changeCron10")
	public String startCron10() {

		stopCron();// 先停止，在开启.
		//future = threadPoolTaskScheduler.schedule(new MyRunnable("000000001",new RedisLockRegistry()), new CronTrigger("*/10 * * * * *"));
		System.out.println("DynamicTask.startCron10()");
		return "changeCron10";
	}


}
