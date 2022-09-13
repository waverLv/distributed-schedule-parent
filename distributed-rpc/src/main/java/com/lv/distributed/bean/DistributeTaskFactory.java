package com.lv.distributed.bean;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface DistributeTaskFactory {

    public DistributeTask newTask();

    public DistributeTask newTask(DistributeRequestBody requestBody, boolean start, ChannelHandlerContext ctx);
}
