package com.lv.distributed.handler;

import com.lv.distributed.bean.DistributeHeader;
import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.common.MessageType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ProjectName: LoginAuthReqHandler
 * @Author: lvminghui
 * @Description: 握手认证channelhandler
 * @Date: 2022/7/11 14:48
 * @Version: 1.0
 */

/**
 * 登录握手请求处理
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    /**
     * Channel active
     *
     * @param ctx ctx
     * @throws Exception exception
     * @since 1.0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //建立管道连接后，构建一个握手请求，由于使用IP白名单的方式进行鉴权，所以不需要携带请求体
        ctx.writeAndFlush(buildLoginReq());
    }

    /**
     * Channel read
     *
     * @param ctx ctx
     * @param msg msg
     * @throws Exception exception
     * @since 1.0
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DistributeRequest message = (DistributeRequest) msg;
        //判断服务端返回的数据，是否是响应请求，如果是，则判断body中的数据是否返回的是 0
        DistributeHeader header = message.getHeader();
        if (header != null && header.getType() == MessageType.LOGIN_RESP.getCode()) {
            Object loginResult = message.getBody();
            if (loginResult !=null &&  (byte)loginResult!= (byte) 0) {;
                //握手失败，关闭连接
                ctx.close();
            } else {
                System.out.println("Login is ok :" + message);
                //透传给后续handler进行处理
                ctx.fireChannelRead(msg);
            }
        } else {
            //透传给后续handler进行处理
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

    /**
     * Build login req
     *
     * @return the netty protocol message
     * @since 1.0
     */
    private DistributeRequest buildLoginReq() {
        DistributeHeader header = new DistributeHeader();
        header.setType(MessageType.LOGIN_REQ.getCode());
        DistributeRequest message = new DistributeRequest();
        message.setHeader(header);
        return message;
    }
}

