package com.lv.distributed.client;

import com.lv.distributed.codec.marshalling.NettyMessageDecoder;
import com.lv.distributed.codec.marshalling.NettyMessageEncoder;
import com.lv.distributed.common.NettyConstant;
import com.lv.distributed.factory.invoke.Invoker;
import com.lv.distributed.handler.HeartBeatReqHandler;
import com.lv.distributed.handler.InvokeRespHandler;
import com.lv.distributed.handler.LoginAuthReqHandler;
import com.lv.distributed.handler.RegisterReqHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient extends Thread{
    private Logger log = LoggerFactory.getLogger(NettyClient.class);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private  EventLoopGroup group = null;
    private  Bootstrap bootstrap = null;
    private  ChannelFuture future = null;
    private  Object source = null;
    private  Invoker invoker;

    public NettyClient(Object source, Invoker invoker){
        this.source = source;
        this.invoker = invoker;
    }

    @Override
    public void run() {
        connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTEIP);
    }

    public void connect(int port, String host){
        try {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_REUSEADDR,true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("messageDecoder",new NettyMessageDecoder(1024*1024, 4, 4,-8,0));
                            socketChannel.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
                            socketChannel.pipeline().addLast(new RegisterReqHandler(source));
                            socketChannel.pipeline().addLast(new InvokeRespHandler(invoker));
//                            socketChannel.pipeline().addLast("loginAuthHandler",new LoginAuthReqHandler());
//                            socketChannel.pipeline().addLast("heartBeatHandler",new HeartBeatReqHandler());

                        }

                    });
            try{
                // 发起异步连接操作
                future = bootstrap.connect(
                        new InetSocketAddress(host, port),
                        new InetSocketAddress(NettyConstant.LOCALIP, NettyConstant.LOCAL_PORT)).sync();
                log.info("Netty client start ok . remoteAddress( "+host+":"+port+"),localAddress("+NettyConstant.LOCALIP+":"+NettyConstant.LOCAL_PORT+")");

                future.channel().closeFuture().sync();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        } finally {
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        // 发起重连操作
                        connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTEIP);
                    } catch (Exception e) {
                        log.error("NettyClient 发起重连操作异常,异常原因：",e);
                    }
                }
            });
        }
    }
}