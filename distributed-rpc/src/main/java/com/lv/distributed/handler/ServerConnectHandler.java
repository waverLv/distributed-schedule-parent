package com.lv.distributed.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @ProjectName: ServerConnectHandler
 * @Author: lvminghui
 * @Description: 服务端连接handler
 * @Date: 2022/9/29 17:10
 * @Version: 1.0
 */
public class ServerConnectHandler extends ChannelHandlerAdapter {
    Logger log = LoggerFactory.getLogger(ServerConnectHandler.class);
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        InetSocketAddress socket = (InetSocketAddress)ctx.channel().remoteAddress();
        log.warn("客户端 【{}:{}】下线！", socket.getHostString(),socket.getPort());
    }
}
