package com.nature.edu.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nature.Response;
import com.nature.edu.config.redis.RedissonManager;
import com.nature.edu.service.IUserService;
import com.nature.edu.util.lock.RedissonRLock;
import com.nature.edu.vo.UserVO;

/**
 * @Description: 用户分布式锁使用测试
 * @author lilun
 * @date 2020-04-16 10:01:04
 * @version 1.0
 */
@RestController
@RequestMapping("/lock")
public class UserLockController {

	private static final Logger logger = LoggerFactory.getLogger(UserLockController.class);

	@Resource
	private RedisLockRegistry redisLockRegistry;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private  RedissonManager redissonManager;	
	

	 
	/**
	 * @Title: infoLockTest
	 * @Description: 分布式锁 简化实现
	 * @param userId
	 * @param lockKey
	 * @return
	 * @author lilun
	 * @date 2020-04-21 02:56:50
	 */
	@GetMapping("/user/infoLock2")
	public Response<UserVO> infoLock2(@RequestParam String userId, @RequestParam String lockKey) {
		logger.info("userId ={}",userId);
		if (StringUtils.isBlank(userId)) {
			logger.error("查询用户信息时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		RLock rLock = redissonManager.getRedisson().getLock(userId);
		Response<UserVO> userVO = RedissonRLock.rlock(rLock).atomic(() -> userService.deductUserMoney(userId),5000,7000);
		//userService.deductUserMoney(userId);
		return userVO;
	}
	@GetMapping("/user/infoLock3")
	public Response<UserVO> infoLock3(@RequestParam String userId, @RequestParam String lockKey) {
		if (StringUtils.isBlank(userId)) {
			logger.error("查询用户信息时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		Response<UserVO> userVO = userService.deductUserMoney(userId);
		return userVO;
	}

	
	/**
	 * 
	 * @Title: infoLockTest
	 * @Description: 分布式锁测试代码
	 * @param userId
	 * @param lockKey
	 * @return
	 * @author lilun
	 * @date 2020-04-21 02:56:50
	 */
	@GetMapping("/user/infoLockTest")
	public String infoLockTest(@RequestParam String userId, @RequestParam String lockKey,@RequestParam String cornStr) {
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		             //dynamicTask.startCron(userId,redisLockRegistry, userService,cornStr);
					infoLock2(userId,lockKey);
				}
			}).start();
		}
		return "测试完成";
		
	}
}
