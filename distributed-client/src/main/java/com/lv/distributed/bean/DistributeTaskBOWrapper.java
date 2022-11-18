package com.lv.distributed.bean;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

public class DistributeTaskBOWrapper extends DistributeTaskBO{

    private ChannelHandlerContext ctx;
    //TODO 是否需要考虑异步
    private boolean async;
    private static final AtomicLong INVOKE_ID = new AtomicLong();
    private final long requestId;

    public DistributeTaskBOWrapper() {
        this.requestId = newId();
    }

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

    public long getRequestId() {
        return requestId;
    }

    private static long newId(){
        return INVOKE_ID.getAndIncrement();
    }
}
