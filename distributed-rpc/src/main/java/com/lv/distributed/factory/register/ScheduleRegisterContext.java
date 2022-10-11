package com.lv.distributed.factory.register;

import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.bean.DistributeTask;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public interface ScheduleRegisterContext {

    public void register(ChannelHandlerContext ctx, DistributeRequest request);

    public void unRegister(ChannelHandlerContext ctx,DistributeRequest request);

}
