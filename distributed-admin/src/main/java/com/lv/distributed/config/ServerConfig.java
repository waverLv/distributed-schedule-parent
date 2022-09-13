package com.lv.distributed.config;

import com.lv.distributed.bootstrap.DistributeScheduleServerBootstrap;
import com.lv.distributed.factory.register.DistributeScheduleRegisterContext;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import com.lv.distributed.server.NettyServer;
import com.lv.distributed.server.ScheduleNettyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: ServerConfig
 * @Author: lvminghui
 * @Description: server config
 * @Date: 2022/9/7 15:24
 * @Version: 1.0
 */
@Configuration
public class ServerConfig {

    @Bean(initMethod = "start",destroyMethod = "destroy")
    public DistributeScheduleServerBootstrap serverBootstrap(){
        DistributeScheduleServerBootstrap bootstrap = new DistributeScheduleServerBootstrap();
        bootstrap.scheduleNettyServer(new ScheduleNettyServer().scheduleRegisterContext(registerContext()));
        bootstrap.scheduleNettyServer(new NettyServer().scheduleRegisterContext(registerContext()));
        return bootstrap;
    }

    @Bean
    public ScheduleRegisterContext registerContext(){
        return new DistributeScheduleRegisterContext();
    }
}
