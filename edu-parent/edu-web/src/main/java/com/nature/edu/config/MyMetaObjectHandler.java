package com.nature.edu.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 
 * @Title: MyMetaObjectHandler.java
 * @Description:  填充策略：字段自动填充处理
 * @author lilun
 * @date 2020-04-16 09:59:18 
 * @version 1.0
 */
@Slf4j
@Component // 一定不要忘记把处理器加到IOC容器中！
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("deleted",0,metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    // 更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill.....");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
