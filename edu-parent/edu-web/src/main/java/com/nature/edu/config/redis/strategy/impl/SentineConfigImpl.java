package com.nature.edu.config.redis.strategy.impl;


import org.apache.commons.lang.StringUtils;
import org.redisson.config.Config;

import com.nature.edu.config.redis.constant.GlobalConstant;
import com.nature.edu.config.redis.strategy.RedissonConfigService;
import com.nature.edu.entity.RedissonProperties;

import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 哨兵集群部署Redis连接配置
 *
 * @author xub
 * @date 2019/6/19 下午9:17
 */
@Slf4j
public class SentineConfigImpl implements RedissonConfigService {



    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getHost();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            //设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            //设置sentinel节点的服务IP和端口
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrTokens[i]);
            }
            log.info("初始化[哨兵部署]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            log.error("哨兵部署 Redisson init error", e);

        }
        return config;
    }
}
