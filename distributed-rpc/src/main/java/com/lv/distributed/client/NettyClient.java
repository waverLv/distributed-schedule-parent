package com.lv.distributed.client;

import com.lv.distributed.bean.ScheduleConfig;
import com.lv.distributed.bean.ServerProperty;
import com.lv.distributed.codec.marshalling.NettyMessageDecoder;
import com.lv.distributed.codec.marshalling.NettyMessageEncoder;
import com.lv.distributed.common.NettyConstant;
import com.lv.distributed.factory.invoke.Invoker;
import com.lv.distributed.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient extends Thread{
    private Logger log = LoggerFactory.getLogger(NettyClient.class);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private  EventLoopGroup group;
    private  Bootstrap bootstrap;
    private  ChannelFuture future;
    private  Object source;
    private  Invoker invoker;
    private ScheduleConfig scheduleConfig;
    private ClientConnectHandler cch = new ClientConnectHandler(this);

    public NettyClient(Object source, Invoker invoker,ScheduleConfig scheduleConfig){
        this.source = source;
        this.invoker = invoker;
        this.scheduleConfig = scheduleConfig;
    }

    @Override
    public void run() {
        connect();
    }

    public void connect(){
        try {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("messageDecoder",new NettyMessageDecoder(1024*1024, 4, 4,-8,0));
                            socketChannel.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
                            socketChannel.pipeline().addLast(new RegisterReqHandler(source));
                            socketChannel.pipeline().addLast(new InvokeRespHandler(invoker));
                            socketChannel.pipeline().addLast(cch);
//                            socketChannel.pipeline().addLast("loginAuthHandler",new LoginAuthReqHandler());
//                            socketChannel.pipeline().addLast("heartBeatHandler",new HeartBeatReqHandler());

                        }

                    });
            doConnect();
        }catch (Exception ex){
            log.error("netty client连接服务器时发生异常,异常原因：",ex);
        }
    }

    private void doConnect(){
        List<ServerProperty> serverProperties = scheduleConfig.getServerProperties();
        if(null == serverProperties || serverProperties.size() == 0){
            throw new RuntimeException("服务端地址配置为null");
        }
        serverProperties.stream().forEach(serverProperty -> doConnect(serverProperty));
    }

    public void doConnect(ServerProperty serverProperty){
        bootstrap.remoteAddress(serverProperty.getHost(),serverProperty.getPort());
        bootstrap.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!future.isSuccess()) {
                    // 连接不成功，5秒后重新连接
                    future.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect(serverProperty);
                        }
                    }, 5, TimeUnit.SECONDS);
                }
            }
        });
        log.info("Netty client start ok . remoteAddress is {}",serverProperty.toString());
    }
}