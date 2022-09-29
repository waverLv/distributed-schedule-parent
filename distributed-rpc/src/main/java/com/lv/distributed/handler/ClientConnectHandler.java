package com.lv.distributed.handler;

import com.lv.distributed.bean.ServerProperty;
import com.lv.distributed.client.NettyClient;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @ProjectName: ClientHandler
 * @Author: lvminghui
 * @Description: netty client处理类
 * @Date: 2022/9/29 16:57
 * @Version: 1.0
 */
public class ClientConnectHandler extends ChannelHandlerAdapter {
    Logger log = LoggerFactory.getLogger(RegisterReqHandler.class);
    private NettyClient nettyClient;

    public ClientConnectHandler(NettyClient nettyClient){
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        int port = ipSocket.getPort();
        String host = ipSocket.getHostString();
        log.error("服务端【" + host + ":" + port + "】下线!");
        ctx.close();
        ctx.deregister();
        ctx.pipeline().remove(this);
        super.channelInactive(ctx);
        nettyClient.doConnect(new ServerProperty(host,port));
    }
}
