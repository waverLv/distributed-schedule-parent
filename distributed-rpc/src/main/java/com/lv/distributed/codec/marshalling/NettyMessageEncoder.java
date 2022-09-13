package com.lv.distributed.codec.marshalling;

import com.lv.distributed.bean.DistributeRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * netty消息协议编码器
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<DistributeRequest> {

    /** Marshalling encoder */
    private final MarshallingEncoder marshallingEncoder;

    /**
     * Netty message encoder
     *
     * @throws IOException io exception
     * @since 1.0
     */
    public NettyMessageEncoder() throws IOException {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    /**
     * Encode
     *
     * @param ctx ctx
     * @param msg msg
     * @param out out
     * @throws Exception exception
     * @since 1.0
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, DistributeRequest msg, List<Object> out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionId());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());

        Map<String, Object> attachment = Optional.ofNullable(msg.getHeader().getAttachment()).orElse(Collections.emptyMap());
        sendBuf.writeInt(attachment.size());
        attachment.forEach((k, v) -> {
            byte[] kBytes = k.getBytes(StandardCharsets.UTF_8);
            //先写入key的长度，在写入key的内容
            sendBuf.writeInt(kBytes.length);
            sendBuf.writeBytes(kBytes);
            //调用序列化器，对value值进行序列化
            this.marshallingEncoder.encode(v, sendBuf);
        });
        //获取到body的数据进行序列化
        Object body = msg.getBody();
        if(body != null) {
            this.marshallingEncoder.encode(body, sendBuf);
        } else {
            sendBuf.writeInt(0);
        }
        sendBuf.setInt(4, sendBuf.readableBytes());
        out.add(sendBuf);
    }
}

