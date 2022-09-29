package com.lv.distributed.handler;

import com.lv.distributed.common.MessageType;
import com.lv.distributed.bean.DistributeHeader;
import com.lv.distributed.bean.DistributeRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class RegisterReqHandler extends ChannelHandlerAdapter {
    Logger log = LoggerFactory.getLogger(RegisterReqHandler.class);

    private Object source;

    public RegisterReqHandler(Object source){
        this.source = source;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DistributeHeader header = new DistributeHeader();
        header.setType(MessageType.REGISTER_REQ.getCode());
        DistributeRequest request = new DistributeRequest();
        request.setHeader(header);
        request.setBody(source);
        ctx.writeAndFlush(request);
    }


}
