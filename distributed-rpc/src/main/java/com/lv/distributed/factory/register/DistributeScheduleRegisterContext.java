package com.lv.distributed.factory.register;

import com.lv.distributed.api.StoreService;
import com.lv.distributed.bean.*;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DistributeScheduleRegisterContext implements ScheduleRegisterContext {

    private DistributeTaskFactory distributeTaskFactory;
    private StoreService storeService;

    public DistributeScheduleRegisterContext(DistributeTaskFactory distributeTaskFactory,StoreService storeService){
        Assert.assertNotNull("storeService不能为空",storeService);
        if(distributeTaskFactory == null){
            distributeTaskFactory = new DefaultDistributeTaskFactory();
        }
        this.storeService = storeService;
        this.distributeTaskFactory = distributeTaskFactory;
    }

    @Override
    public void register(ChannelHandlerContext ctx,Object msg) {
        DistributeRequest request = (DistributeRequest) msg;
        List<DistributeRequestBody> requestBodyList = (List<DistributeRequestBody>) request.getBody();
        requestBodyList.forEach(requestBody -> {
            registerApplicationGroup(ctx,requestBody);
            //TODO 1、注册任务 2、注册成功后判定任务是否已经启动 3、若启动，添加到时间轮
//            storeService.store(bo2Task(requestBody));
        });

    }

    @Override
    public void unRegister(ChannelHandlerContext ctx, Object msg) {

    }

    /**
     * 将任务注册进全量任务列表
     * @param ctx  通信通道
     * @param distributeRequestBody  定时任务注册实体
     */
    private void registerApplicationGroup(ChannelHandlerContext ctx,DistributeRequestBody distributeRequestBody){
        RegisterChannelContext.put(distributeRequestBody.getApplicationName(),ctx);
    }


}
