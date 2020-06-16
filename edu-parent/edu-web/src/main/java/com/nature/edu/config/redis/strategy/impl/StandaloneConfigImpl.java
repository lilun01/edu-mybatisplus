package com.nature.edu.config.redis.strategy.impl;



import org.apache.commons.lang.StringUtils;
import org.redisson.config.Config;
import com.nature.edu.config.redis.constant.GlobalConstant;
import com.nature.edu.config.redis.strategy.RedissonConfigService;
import com.nature.edu.entity.RedissonProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 单机部署Redisson配置
 *
 * @author xub
 * @date 2019/6/19 下午10:04
 */
@Slf4j
public class StandaloneConfigImpl implements RedissonConfigService {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getHost();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + address+":"+redissonProperties.getPort();
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            //密码可以为空
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            log.info("初始化[单机部署]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            log.error("单机部署 Redisson init error", e);
        }
        return config;
    }
}
