package com.lv.distributed.handler;

import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.common.MessageType;
import com.lv.distributed.factory.invoke.Invoker;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ProjectName: InvokeReqHandler
 * @Author: lvminghui
 * @Description: 定时任务调度请求Handler
 * @Date: 2022/9/9 14:19
 * @Version: 1.0
 */
public class InvokeReqHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(InvokeRespHandler.class);

    private Invoker invoker;

    public InvokeReqHandler(Invoker invoker){
        this.invoker = invoker;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DistributeRequest request = (DistributeRequest) msg;
        if(null != request){
            invoker.callback(request);
        }
        ctx.fireChannelRead(msg);
    }
}
