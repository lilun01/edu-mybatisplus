package com.nature.edu.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.nature.Response;
import com.nature.edu.config.redis.RedissonManager;
import com.nature.edu.entity.BasUser;
import com.nature.edu.entity.UserAddress;
import com.nature.edu.feign.OrderFeignClient;
import com.nature.edu.service.IUserService;
import com.nature.edu.util.lock.RedissonLock;
import com.nature.edu.util.lock.RedissonRLock;
import com.nature.edu.vo.UserVO;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired
    private RedissonLock redissonLock;

    @Resource
    private OrderFeignClient orderFeignClient;
    
    @Autowired
	private IUserService userService;

    @Resource
    private UserController userController;
    @Autowired
	private  RedissonManager redissonManager;


    @SentinelResource(value = "create", blockHandler = "execptionHandler")
    @PostMapping("/create")
    public String create() {
        long startTime = System.currentTimeMillis();
        boolean result = orderFeignClient.create("U100000", "C100000", 30);
        long endTime = System.currentTimeMillis();
        float f = (endTime - startTime) / 1000f;
        System.out.println("调用结果=" + result + ",耗时：" + (endTime - startTime) / 1000f + "s");
        return result + ",耗时：" + f;
    }


    @RequestMapping("/create2")
    public String create2() {
        String s = userController.testSentinel(null);
        return s;
    }
    
    @RequestMapping("/createManyOrder")
    public Response<UserVO> createManyOrder() {
    	  redissonLock.lock("402850A6717D0AA701717D0B6FCA0001", 10L);
          
          //如果该线程还持有该锁，那么释放该锁。如果该线程不持有该锁，说明该线程的锁已到过期时间，自动释放锁
    	 Response<UserVO> user = userService.getUser("402850A6717D0AA701717D0B6FCA0001");
    	 if(user.getData().getMoney() >0) {
    		 System.out.println("钱数等于="+user.getData().getMoney()+"，可以创建订单=========");
    	 }else {
    		 System.out.println("钱数等于="+user.getData().getMoney()+"，不能创建订单");
    	 }
    	 if (redissonLock.isHeldByCurrentThread("402850A6717D0AA701717D0B6FCA0001")) {
    		 redissonLock.unlock("402850A6717D0AA701717D0B6FCA0001");
    	 }
    	return user;
    }
    @RequestMapping("/createManyOrder2")
    public Response<UserVO> createManyOrder2() {
    	RLock rLock = redissonManager.getRedisson().getLock("402850A6717D0AA701717D0B6FCA0001");
    	Response<UserVO> user = RedissonRLock.rlock(rLock).atomic(() -> userService.getUser("402850A6717D0AA701717D0B6FCA0001"),5000,7000);
    	if(user.getData().getMoney() >0) {
    		System.out.println("钱数等于="+user.getData().getMoney()+"，可以创建订单=========");
    	}else {
    		System.out.println("钱数等于="+user.getData().getMoney()+"，不能创建订单============");
    	}
    	if (redissonLock.isHeldByCurrentThread("402850A6717D0AA701717D0B6FCA0001")) {
    		redissonLock.unlock("402850A6717D0AA701717D0B6FCA0001");
    	}
    	return user;
    }
    
	@RequestMapping("/optionalTest")
	public String optionalTest() {
		BasUser user = new BasUser();
		user.setUserName("lisi");
		
		UserAddress address = new UserAddress();
		address.setDeleted(0);
		user.setUserAddress(address);
		//address.setAddress("四川");
		log.info("user={}",JSONUtil.toJsonStr(user));
		String name = Optional.ofNullable(user).map(BasUser::getUserAddress).map(UserAddress::getAddress).orElse("没有符合条件的字符串");
		//String name2 = Optional.ofNullable(user).map(BasUser::getUserAddress).map(UserAddress::getAddress).orElseThrow(()->{throw new IllegalStateException("没有存在的值");});

		return name;
	}


}
