package com.lv.distributed.bean;

import io.netty.channel.ChannelHandlerContext;

public interface  DistributeTask extends Runnable {


    public String getUniqueTaskName();
    public String getGroupName();
    public DistributeRequestBody requestBody();
    public ChannelHandlerContext getCtx();
}
