package com.lv.distributed.handler;

import com.lv.distributed.bean.DistributeHeader;
import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.common.MessageType;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ProjectName: HeartBeatReqHandler
 * @Author: lvminghui
 * @Description: 心跳检测channelhandler
 * @Date: 2022/7/11 15:36
 * @Version: 1.0
 */

/**
 * 心跳响应处理器
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DistributeRequest nettyProtocolMessage = (DistributeRequest) msg;
        DistributeHeader header = nettyProtocolMessage.getHeader();
        if (header != null && MessageType.HEARTBEAT_REQ.getCode() == header.getType()) {
            System.out.println("Receive client heart beat message : ---->" + nettyProtocolMessage);
            DistributeRequest heartBeat = buildHeartBeat();
            System.out.println("Send heart beat response message to client : --->" + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private DistributeRequest buildHeartBeat() {
        DistributeHeader header = new DistributeHeader();
        header.setType(MessageType.HEARTBEAT_RESP.getCode());
        DistributeRequest message = new DistributeRequest();
        message.setHeader(header);
        return message;
    }
}

