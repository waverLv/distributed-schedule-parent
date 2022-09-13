package com.lv.distributed.bean;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class DefaultDistributeTaskFactory implements DistributeTaskFactory{
    @Override
    public DistributeTask newTask() {
        return null;
    }

    @Override
    public DistributeTask newTask(DistributeRequestBody requestBody, boolean start, ChannelHandlerContext ctx) {
        return new DefaultDistributeTask(requestBody,start,ctx);
    }
}
