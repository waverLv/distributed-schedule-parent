package com.lv.distributed.bootstrap;

import com.lv.distributed.server.NettyServer;
import com.lv.distributed.server.ScheduleNettyServer;
import org.junit.Assert;

/**
 * @ProjectName: DistributeScheduleServerBootstrap
 * @Author: lvminghui
 * @Description: 分布式定时任务服务端启动辅助类
 * @Date: 2022/9/9 15:28
 * @Version: 1.0
 */
public class DistributeScheduleServerBootstrap{

    private NettyServer nettyServer;

    public DistributeScheduleServerBootstrap scheduleNettyServer(NettyServer nettyServer){
        this.nettyServer = nettyServer;
        return this;
    }


    public void destroy(){
        nettyServer.shutdownGracefully();
    }

    public void start(){
        Assert.assertNotNull("nettyServer不能为空",nettyServer);
        nettyServer.start();
    }
}
