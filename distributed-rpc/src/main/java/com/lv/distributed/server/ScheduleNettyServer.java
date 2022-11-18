package com.lv.distributed.server;

import com.lv.distributed.codec.marshalling.NettyMessageDecoder;
import com.lv.distributed.codec.marshalling.NettyMessageEncoder;
import com.lv.distributed.factory.register.ScheduleRegisterContext;
import com.lv.distributed.handler.HeartBeatRespHandler;
import com.lv.distributed.handler.LoginAuthRespHandler;
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

public class ScheduleNettyServer extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ScheduleNettyServer.class);
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture future;
    private ScheduleRegisterContext scheduleRegisterContext;
    private int port = 20000;

    public ScheduleNettyServer scheduleRegisterContext(ScheduleRegisterContext scheduleRegisterContext){
        this.scheduleRegisterContext = scheduleRegisterContext;
        return this;
    }
    @Override
    public void run(){
        try{
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("messageDecoder",new NettyMessageDecoder(1024*1024, 4, 4,-8,0));
                            socketChannel.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
//                            socketChannel.pipeline().addLast(new RegisterRespHandler(scheduleRegisterContext));
//                            socketChannel.pipeline().addLast(new InvokeReqHandler());
                            socketChannel.pipeline().addLast("LoginAuthRespHandler",new LoginAuthRespHandler());
                            socketChannel.pipeline().addLast("HeartBeatHandler",new HeartBeatRespHandler());
                        }
                    });
            future = bootstrap.bind(port).sync();
            log.info("schedule admin启动，绑定端口号：{}",port);
            future.channel().closeFuture().sync();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
           destroy();
        }

    }

    /**
     * 优雅销毁方法
     */
    public void destroy(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
