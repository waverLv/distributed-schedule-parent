package com.lv.distributed.bean;

import io.netty.channel.ChannelHandlerContext;

public class DistributeTaskBOWrapper extends DistributeTaskBO{

    private ChannelHandlerContext ctx;
    //TODO 是否需要考虑异步
    private boolean async;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
