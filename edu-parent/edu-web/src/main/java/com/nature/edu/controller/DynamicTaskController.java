package com.nature.edu.controller;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
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
	//public String add(@RequestParam String userId, @RequestParam String lockKey,@RequestParam String cornStr) {
		//return null;
		String cornStr = DateCoreUtil.getCron(date);
		System.out.println("cornStr="+cornStr);
		
		//多线程模拟
		/*
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
						dynamicTask.startCron(userId,redisLockRegistry, userService,cornStr);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		*/
		dynamicTask.startCron(userId,redisLockRegistry, userService,cornStr);
		return "测试完成";
		
	}
	
	/**
	 * @Description: 得到有哪些任务需要执行
	 * @return
	 * @author lilun
	 * @date 2020-04-22 11:14:30
	 */
	@GetMapping("/task/getMap")
	public String getMap() {
		String jsonString = JSON.toJSONString(DynamicTask.scheduledFutureMap);
		return jsonString;
	}
	
	/**
	 * @Description: 停止
	 * @param userId  指定key
	 * @return
	 * @author lilun
	 * @date 2020-04-22 11:14:06
	 */
	@GetMapping("/task/stop")
	public String stop(@RequestParam String userId) {
		ScheduledFuture<?> scheduledFuture = DynamicTask.scheduledFutureMap.get(userId);
		if(scheduledFuture != null && !scheduledFuture.isCancelled()) {
			scheduledFuture.cancel(true);
			DynamicTask.scheduledFutureMap.remove(userId);
		}
		return JSON.toJSONString(scheduledFuture);
	}
	
	/**
	 * @Description:  改变执行时间
	 * @param userId
	 * @param cornStr
	 * @return
	 * @author lilun
	 * @date 2020-04-22 11:13:49
	 */
	@RequestMapping("/task/changeCron")
    //public String changeCron(@RequestParam String userId,@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date) {
	public String changeCron(@RequestParam String userId,@RequestParam String cornStr) {
	   stop(userId);// 先停止，在开启
	   ////String cornStr = DateCoreUtil.getCron(date);
       dynamicTask.startCron(userId,redisLockRegistry, userService,cornStr);
       System.out.println("DynamicTaskController.changeCron()");
       return "changeCron";
    }
	
	
}
