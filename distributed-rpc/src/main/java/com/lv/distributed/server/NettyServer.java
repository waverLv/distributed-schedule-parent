package com.lv.distributed.server;

import com.lv.distributed.codec.marshalling.NettyMessageDecoder;
import com.lv.distributed.codec.marshalling.NettyMessageEncoder;
import com.lv.distributed.common.NettyConstant;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import com.lv.distributed.handler.HeartBeatRespHandler;
import com.lv.distributed.handler.InvokeReqHandler;
import com.lv.distributed.handler.LoginAuthRespHandler;
import com.lv.distributed.handler.RegisterRespHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer extends Thread{
    private Logger log = LoggerFactory.getLogger(ScheduleNettyServer.class);
    private ScheduleRegisterContext scheduleRegisterContext;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;

    public NettyServer scheduleRegisterContext(ScheduleRegisterContext scheduleRegisterContext){
        this.scheduleRegisterContext = scheduleRegisterContext;
        return this;
    }

    public void run() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .option(ChannelOption.SO_REUSEADDR,true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("messageDecoder",new NettyMessageDecoder(1024*1024, 4, 4,-8,0));
                        socketChannel.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
                        socketChannel.pipeline().addLast(new RegisterRespHandler(scheduleRegisterContext));
//                            socketChannel.pipeline().addLast(new InvokeReqHandler());
//                        socketChannel.pipeline().addLast("LoginAuthRespHandler",new LoginAuthRespHandler());
//                        socketChannel.pipeline().addLast("HeartBeatHandler",new HeartBeatRespHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(NettyConstant.REMOTEIP,NettyConstant.REMOTE_PORT).sync();
            log.info("Netty server start ok : "+(NettyConstant.REMOTEIP+":"+ NettyConstant.REMOTE_PORT));
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}