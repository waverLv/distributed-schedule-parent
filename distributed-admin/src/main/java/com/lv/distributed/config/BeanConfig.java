package com.lv.distributed.config;

import com.lv.distributed.monitor.EventDispatch;
import com.lv.distributed.wheel.SystemTimer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: BeanConfig
 * @Author: lvminghui
 * @Description: bean配置类
 * @Date: 2022/9/19 15:55
 * @Version: 1.0
 */
@Configuration
public class BeanConfig {

    @Bean(initMethod = "start")
    public SystemTimer timer(EventDispatch eventDispatch){
        return new SystemTimer("distribute-schedule-",1000l,20,System.currentTimeMillis(),eventDispatch);
    }
}
