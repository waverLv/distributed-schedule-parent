package com.lv.distributed.factory.register;

import com.lv.distributed.bean.DefaultDistributeTaskFactory;
import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.bean.DistributeRequestBody;
import com.lv.distributed.bean.DistributeTaskFactory;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class DistributeScheduleRegisterContext implements ScheduleRegisterContext {

    private DistributeTaskFactory distributeTaskFactory;

    public DistributeScheduleRegisterContext(){
        this(null);
    }
    public DistributeScheduleRegisterContext(DistributeTaskFactory distributeTaskFactory){
        if(distributeTaskFactory == null){
            distributeTaskFactory = new DefaultDistributeTaskFactory();
        }
        this.distributeTaskFactory = distributeTaskFactory;
    }

    @Override
    public void register(ChannelHandlerContext ctx,DistributeRequest request) {
        List<DistributeRequestBody> requestBodyList = (List<DistributeRequestBody>) request.getBody();
        requestBodyList.forEach(requestBody -> {
            registerApplicationGroup(ctx,requestBody);
            //TODO 1、注册任务 2、注册成功后判定任务是否已经启动 3、若启动，添加到时间轮
        });

    }

    @Override
    public void unRegister(ChannelHandlerContext ctx, DistributeRequest request) {

    }

    /**
     * 将任务注册进全量任务列表
     * @param ctx  通信通道
     * @param distributeRequestBody  定时任务注册实体
     */
    private void  registerApplicationGroup(ChannelHandlerContext ctx,DistributeRequestBody distributeRequestBody){
        RegisterChannelContext.put(distributeRequestBody.getApplicationName(),ctx);
    }


}
