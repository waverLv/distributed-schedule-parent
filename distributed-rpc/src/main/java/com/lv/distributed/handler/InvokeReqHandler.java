package com.lv.distributed.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ProjectName: InvokeReqHandler
 * @Author: lvminghui
 * @Description: 定时任务调度请求Handler
 * @Date: 2022/9/9 14:19
 * @Version: 1.0
 */
public class InvokeReqHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }
}
