package com.nature.edu.task.interfaceTask;

/**
 * 
 * @Title: CallableTask.java
 * @Description:  有返回值的函数式接口
 * @author lilun
 * @date 2020-04-23 05:24:38 
 * @version 1.0
 */
@FunctionalInterface
public interface CallableTask<T> {

  T call() throws Exception;
}
