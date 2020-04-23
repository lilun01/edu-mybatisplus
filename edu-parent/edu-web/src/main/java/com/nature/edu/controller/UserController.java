package com.nature.edu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Throwables;
import com.nature.Response;
import com.nature.edu.service.IUserService;
import com.nature.edu.util.lock.RedissonRLock;
import com.nature.edu.vo.UserVO;

/**
 * @Title: UserController.java
 * @Description: 用户类
 * @author lilun
 * @date 2020-04-16 10:01:04
 * @version 1.0
 */
@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private RedisLockRegistry redisLockRegistry;
	@Autowired
	private IUserService userService;
	
	@Resource
	private RedissonClient redissonClient;

	
	
	
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/user/add")
	public Response<Boolean> addUser(@RequestBody UserVO user) {
		if (user == null) {
			logger.error("添加用户时，用户信息不能为空'");
			return Response.failResult("用户信息不能为空");
		}
		String personName = user.getPersonName();
		if (StringUtils.isBlank(personName)) {
			logger.error("添加用户时，personName不能为空'");
			return Response.failResult("用户姓名不能为空");
		}
		return userService.addUser(user);
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/user/modify")
	public Response<Boolean> modifyUser(UserVO user) {
		if (user == null) {
			logger.error("修改用户时，用户信息不能为空'");
			return Response.failResult("用户信息不能为空");
		}
		String userId = user.getUserId();
		if (StringUtils.isBlank(userId)) {
			logger.error("修改用户时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		return userService.modifyUser(user);
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/delete")
	public Response<Boolean> deleteUser(@RequestParam String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.error("删除用户时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		return userService.deleteUser(userId);
	}

	/**
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/info")
	public Response<UserVO> getUser(@RequestParam String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.error("查询用户信息时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		return userService.getUser(userId);
	}

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
	@GetMapping("/user/infoLock")
	public Response<UserVO> infoLock(@RequestParam String userId, @RequestParam String lockKey) {
		if (StringUtils.isBlank(userId)) {
			logger.error("查询用户信息时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		Response<UserVO> user = null;
		Lock lock = redisLockRegistry.obtain(lockKey);
		try {
			//尝试加锁时间为3s，加不上则结束
			boolean getLockResult = lock.tryLock(5, TimeUnit.SECONDS);
			System.out.println("获取锁结果：" + getLockResult);
			if (getLockResult) {
				 logger.info("{}-{}获取锁={}", Thread.currentThread(),new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				user = userService.getUser(userId);
				TimeUnit.SECONDS.sleep(1);
				System.out.println("获取锁成功！！！");
				// TODO 具体业务
			} else {
				UserVO userVo = new UserVO();
				userVo.setMsg("获取锁失败！！");
				Response<UserVO> failResult = Response.failResult("获取锁失败！！！", userVo);
				return failResult;
			}
		} catch (Exception e) {
			logger.error("获取锁失败={}", Throwables.getStackTraceAsString(e));
		} finally {
			logger.info("{}-{}开始释放锁", Thread.currentThread(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			try {
				lock.unlock();
				logger.info("释放锁成功");
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("没有获取到锁，不用释放");
			}
		}
		return user;
	}
	@GetMapping("/user/infoLock2")
	public Response<UserVO> infoLock2(@RequestParam String userId, @RequestParam String lockKey) {
		if (StringUtils.isBlank(userId)) {
			logger.error("查询用户信息时，userId不能为空'");
			return Response.failResult("用户Id不能为空");
		}
		Response<UserVO> user = null;
		RLock rLock = redissonClient.getLock(userId);
		RedissonRLock.rlock(rLock).atomic(() -> userService.deductUserMoney(userId),5000,7000);
		//userService.deductUserMoney(userId);
		return user;
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
			//new Thread(()->infoLock2(userId,lockKey)).start();
			
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

	/**
	 * 分页查询用户列表
	 * 
	 * @param searchName 可为空
	 * @param pageNo     可为空 默认为1
	 * @param pageSize   可为空 默认为10
	 * @return
	 */
	@PostMapping("/user/page")
	public Response<Page<UserVO>> getUserPage(String searchName, String pageNo, String pageSize) {
		Page<UserVO> page = new Page<>();
		if (StringUtils.isNotBlank(pageNo)) {
			page.setCurrent(Long.parseLong(pageNo));
		}
		if (StringUtils.isNotBlank(pageSize)) {
			page.setSize(Long.parseLong(pageSize));
		}
		return userService.getUserPage(searchName, page);
	}
}
