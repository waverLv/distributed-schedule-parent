package com.lv.distributed.handler;

import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.common.MessageType;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class RegisterRespHandler extends ChannelHandlerAdapter {

    private ScheduleRegisterContext registerContext;

    public RegisterRespHandler(ScheduleRegisterContext registerContext){
        this.registerContext = registerContext;
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DistributeRequest request = (DistributeRequest) msg;
        if(null != request && request.getHeader().getType() == MessageType.REGISTER_REQ.getCode()){
            registerContext.register(ctx,msg);
        }
        ctx.fireChannelRead(msg);
    }
}
