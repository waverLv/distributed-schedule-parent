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
