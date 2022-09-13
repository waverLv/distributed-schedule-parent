package com.lv.distributed.bean;

import io.netty.channel.ChannelHandlerContext;

public class DefaultDistributeTask extends AbstractDistributeTask{

    public DefaultDistributeTask(DistributeRequestBody body, boolean start, ChannelHandlerContext ctx) {
        super(body, start,ctx);
    }

}
