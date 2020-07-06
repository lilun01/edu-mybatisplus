package com.nature.edu.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;

import com.nature.Response;
import com.nature.edu.config.redis.RedissonManager;
import com.nature.edu.service.IUserService;
import com.nature.edu.vo.UserVO;

/**
 * 
 * @Title: TaskRunnable.java
 * @Description: 任务执行实现类
 * @author lilun
 * @date 2020-04-22 11:16:46 
 * @version 1.0
 */
public class TaskRunnable implements Runnable{

	private RedisLockRegistry redisLockRegistry;
	public String corn;
	
	@Autowired
	private  RedissonManager redissonManager;
	
	//锁名称，一般使用唯一值
	public String lockKey;
	
	@Autowired
	private IUserService userService;
	
	public TaskRunnable(String lockKey,RedisLockRegistry redisLockRegistry ,IUserService userService,String corn) {
		this.corn = corn;
		this.userService = userService;
		this.redisLockRegistry = redisLockRegistry;
		this.lockKey = lockKey;
		
	}
	

	
	/**
	 * @Description: 分布式锁实现 保证分布式环境下不重复执行
	 * @return
	 * @author lilun
	 * @date 2020-04-21 06:12:48
	 */
	
	@Override
	public void run() {
		Lock lock = redisLockRegistry.obtain(lockKey);
		boolean isLock = false;
		try {
			 isLock = lock.tryLock(5, TimeUnit.SECONDS);
			if(isLock) {
				Response<UserVO> user = userService.getUserNOCache(lockKey);
				if("1".equals(user.getData().getNickName())) {//模拟已经执行
					System.out.println("id="+lockKey+ ",corn="+corn+ ",已经执行了定时任务，不再执行，直接返回");
					DynamicTask.scheduledFutureMap.remove(lockKey);
					return;
				}else {
					user.getData().setNickName("1");
					userService.modifyUser(user.getData());
				}
				System.out.println("id="+lockKey+ ",corn="+corn+ ",定时任务锁获取成功,开始执行定时任务。。。。。。");
				//TODO 模拟业务执行
				TimeUnit.SECONDS.sleep(1);
				DynamicTask.scheduledFutureMap.remove(lockKey);
			}else {
				System.out.println("id="+lockKey+ ",corn="+corn+ ",定时任务锁获取失败");
				return;
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(isLock) {
				lock.unlock();
				System.out.println("id="+lockKey+ ",corn="+corn+",释放锁成功");
			}else {
				System.out.println("id="+lockKey+ ",corn="+corn+",没有获取到锁，不用释放");
			}
		}
		
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName+ "===DynamicTask.MyRunnable.run(),id=" + lockKey);
	}
	
	
	
	
	
	
}
