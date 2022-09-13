package com.lv.distributed.handler;

import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.common.MessageType;
import com.lv.distributed.factory.invoke.Invoker;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ProjectName: InvokeRespHandler
 * @Author: lvminghui
 * @Description: 定时任务调度响应Handler
 * @Date: 2022/9/9 14:20
 * @Version: 1.0
 */
public class InvokeRespHandler extends ChannelHandlerAdapter {
    Logger log = LoggerFactory.getLogger(InvokeRespHandler.class);

    private Invoker invoker;

    public InvokeRespHandler(Invoker invoker){
        this.invoker = invoker;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DistributeRequest request = (DistributeRequest) msg;
        if(null != request && request.getHeader().getType() == MessageType.INVOKE_REQ.getCode()){
            invoker.invoke(request);
            log.info("调用成功,request="+request.toString());
        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
