package com.nature.edu.util.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

import com.nature.edu.task.interfaceTask.CallableTask;
import com.nature.edu.task.interfaceTask.RunnableTask;

import lombok.extern.slf4j.Slf4j;

/**
 * Redisson可重入锁工具
 * @author mayuhan
 * @date 2019/6/19 20:42
 */
@Slf4j
public class RedissonRLock {

	/** 默认等待时间 */
	private final static long DEFAULT_WAIT_TIME = 3000;

	/** 默认释放时间 */
	private final static long DEFAULT_LEASE_TIME = 3000;

	/** 可重入锁 */
	private RLock rLock;

	private RedissonRLock(RLock lock) {
		this.rLock = lock;
	}

	/**
	 * 静态构造方法
	 * @param rLock RLock实例
	 * @return 返回工具对象
	 */
	public static RedissonRLock rlock(RLock rLock) {
		checkParam(rLock);
		return new RedissonRLock(rLock);
	}

	/**
	 * 检查参数是否为空
	 */
	private static void checkParam(Object o) {
		if (o == null) {
			throw new IllegalArgumentException("Invalid RLock, param is null");
		}
	}

	/**
	 * 执行原子操作。
	 * 指定获取锁的等待时间，并尝试自动释放锁。
	 * 缺点：超过等待时间后，会放弃执行，直接返回false；
	 * @param operation 操作方法
	 * @param waitTime 获取锁等待时间
	 * @param leaseTime 获取锁后释放锁时间
	 * @throws RuntimeException {@code lock}为空，抛出；方法执行错误，抛出；
	 */
	public void atomic(RunnableTask operation, long waitTime, long leaseTime) {
		boolean isLock = false;
		try {
			// 加锁
			isLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
			// 是否获取到锁
			if (isLock) {
				operation.run();
			}else {
				log.error("获取锁失败");
				return;
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			log.error("Redisson加锁方法执行异常：", e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			// 获取到锁，才解锁
			if (isLock) {
				rLock.unlock();
			}
		}
	}
	
	/**
	 * 执行原子操作，获取返回值。
	 * 指定获取锁的等待时间，并尝试自动释放锁。
	 * 缺点：超过等待时间后，会放弃执行，直接返回false；
	 * @param operation 操作方法
	 * @param waitTime 获取锁等待时间
	 * @param leaseTime 获取锁后释放锁时间
	 * @throws RuntimeException {@code lock}为空，抛出；方法执行错误，抛出；
	 * @return 操作的返回值，如果未获取到锁，也会返回null
	 */
	public <T> T atomic(CallableTask<T> operation, long waitTime, long leaseTime) {
		boolean isLock = false;
		T t = null;
		try {
			// 加锁
			isLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
			// 是否获取到锁
			if (isLock) {
				t = operation.call();
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			log.error("Redisson加锁方法执行异常：", e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			// 获取到锁，才解锁
			if (isLock) {
				rLock.unlock();
			}
		}

		return t;
	}
	public <T> T lock(CallableTask<T> operation, long waitTime, long leaseTime) {
		boolean isLock = false;
		T t = null;
		try {
			rLock.lock(leaseTime, TimeUnit.MILLISECONDS);
			
			// 加锁
			isLock = rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
			// 是否获取到锁
			if (isLock) {
				t = operation.call();
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			log.error("Redisson加锁方法执行异常：", e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			// 获取到锁，才解锁
			if (isLock) {
				rLock.unlock();
			}
		}
		
		return t;
	}


	/**
	 * 采用默认时间参数的原子操作
	 * @param operation 操作方法
	 * @see RedissonRLock#atomic(com.sensin.util.thread.RunnableThrowsException, long, long)
	 */
	public void atomic(RunnableTask operation) {
		atomic(operation, DEFAULT_WAIT_TIME, DEFAULT_LEASE_TIME);
	}
}
