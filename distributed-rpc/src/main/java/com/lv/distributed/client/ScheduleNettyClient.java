package com.lv.distributed.client;

import com.lv.distributed.codec.marshalling.NettyMessageDecoder;
import com.lv.distributed.codec.marshalling.NettyMessageEncoder;
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

public class ScheduleNettyClient extends Thread{
    public static EventLoopGroup group = null;
    public static Bootstrap bootstrap = null;
    public static ChannelFuture future = null;
    public static Object source = null;

    public ScheduleNettyClient(Object source){
        this.source = source;
    }

    public void run(){
        try{
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("messageDecoder",new NettyMessageDecoder(1024*1024, 4, 4,-8,0));
                            socketChannel.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
//                            socketChannel.pipeline().addLast(new RegisterReqHandler(source));
                            socketChannel.pipeline().addLast("loginAuthHandler",new LoginAuthReqHandler());
                            socketChannel.pipeline().addLast("heartBeatHandler",new HeartBeatReqHandler());
//                            socketChannel.pipeline().addLast(new InvokeRespHandler());
                        }
                    });
            future = bootstrap.connect("localhost", 20000).sync();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
