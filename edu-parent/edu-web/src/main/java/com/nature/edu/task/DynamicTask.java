package com.nature.edu.task;

import java.util.concurrent.ConcurrentHashMap;
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
	private int taskSchedulerCorePoolSize = 10;
	
	private ScheduledFuture<?> future;
	// 线程存储器
	@SuppressWarnings("rawtypes")
	public static ConcurrentHashMap<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<String, ScheduledFuture>();
	
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(taskSchedulerCorePoolSize);
	    threadPoolTaskScheduler.initialize();
		return threadPoolTaskScheduler;
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
		scheduledFutureMap.put(id, future);
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
