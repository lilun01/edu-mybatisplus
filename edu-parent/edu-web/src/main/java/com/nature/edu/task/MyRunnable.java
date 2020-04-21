package com.nature.edu.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;

import com.nature.Response;
import com.nature.edu.service.IUserService;
import com.nature.edu.vo.UserVO;

public class MyRunnable implements Runnable{

	private RedisLockRegistry redisLockRegistry;
	public String corn;
	public String id;
	
	@Autowired
	private IUserService userService;
	
	public MyRunnable(String id,RedisLockRegistry redisLockRegistry ,IUserService userService,String corn) {
		this.corn = corn;
		this.userService = userService;
		this.redisLockRegistry = redisLockRegistry;
		this.id = id;
		
	}
	
	/**
	 * @Description: 分布式锁实现 不重复执行
	 * @return
	 * @author lilun
	 * @date 2020-04-21 06:12:48
	 */
	@Override
	public void run() {
		Lock lock = redisLockRegistry.obtain(id);
		try {
			boolean lockResult = lock.tryLock(5, TimeUnit.SECONDS);
			if(lockResult) {
				Response<UserVO> user = userService.getUserNOCache(id);
				if("1".equals(user.getData().getNickName())) {
					System.out.println("id="+id+ ",corn="+corn+ "已经执行了定时任务，不在执行，直接返回");
					return;
				}else {
					user.getData().setNickName("1");
					userService.modifyUser(user.getData());
				}
				System.out.println("id="+id+ ",corn="+corn+ ",定时任务锁获取成功,开始执行定时任务。。。。。。");
				TimeUnit.SECONDS.sleep(1);
			}else {
				System.out.println("id="+id+ ",corn="+corn+ "定时任务锁获取失败");
				return;
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			try {
				lock.unlock();
				System.out.println("id="+id+ ",corn="+corn+"释放锁成功");
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("id="+id+ ",corn="+corn+"没有获取到锁，不用释放");
			}
		}
		
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName+ "===DynamicTask.MyRunnable.run(),id=" + id);
	}
}
