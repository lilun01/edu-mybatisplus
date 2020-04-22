package com.nature.edu.task.interfaceTask;

/**
 * 
 * @Title: RunnableThrowsException.java
 * @Description: 定时器执行类
 * @author lilun
 * @date 2020-04-22 11:42:22 
 * @version 1.0
 */

@FunctionalInterface
public interface RunnableTask {

	void run() throws Exception;
}
