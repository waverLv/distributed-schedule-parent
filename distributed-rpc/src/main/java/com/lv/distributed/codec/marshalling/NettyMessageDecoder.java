package com.lv.distributed.codec.marshalling;

import com.lv.distributed.bean.DistributeHeader;
import com.lv.distributed.bean.DistributeRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * netty消息解码处理器：LengthFieldBasedFrameDecoder 对定长包进行处理
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {

    /**
     * 创建解码器对象
     */
    private final MarshallingDecoder marshallingDecoder;

    /**
     * Netty message decoder
     *
     * @param maxFrameLength      包的最大长度
     * @param lengthFieldOffset   字段偏移位置，比如在 buf.index为4的地方表示消息长度
     * @param lengthFieldLength   消息长度字段的长度，例如int：就4个字节
     * @param lengthAdjustment    长度矫正，如果不配置这里会导致读出来的长度比数据包的长度长，导致解析不了
     * @param initialBytesToStrip 初始需要跳过的字节长度
     * @throws IOException io exception
     * @since 1.0
     */
    public NettyMessageDecoder(int maxFrameLength,
                               int lengthFieldOffset,
                               int lengthFieldLength,
                               int lengthAdjustment,
                               int initialBytesToStrip) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        this.marshallingDecoder = new MarshallingDecoder();
    }

    /**
     * Decode
     *
     * @param ctx ctx
     * @param in  in
     * @return the object
     * @throws Exception exception
     * @since 1.0
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        /**
         * 默认使用netty的包长度解码器
         */
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if (byteBuf == null) {
            return null;
        }
        DistributeRequest message = new DistributeRequest();
        DistributeHeader header = new DistributeHeader();
        header.setCrcCode(byteBuf.readInt());
        header.setLength(byteBuf.readInt());
        header.setSessionId(byteBuf.readLong());
        header.setType(byteBuf.readByte());
        header.setPriority(byteBuf.readByte());
        int size =  byteBuf.readInt();
        if(size > 0){
            Map<String,Object> attch = new HashMap<>();
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for(int i=0; i<size; i++){
                keySize = byteBuf.readInt();
                keyArray = new byte[keySize];
                byteBuf.readBytes(keyArray);
                key = new String(keyArray,"UTF-8");
                attch.put(key,marshallingDecoder.decode(byteBuf));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attch);
        }
        if(byteBuf.readableBytes() > 4){
            message.setBody( marshallingDecoder.decode(byteBuf));
        }
        message.setHeader(header);
        return message;
    }

    /**
     * Gets marshalling decoder *
     *
     * @return the marshalling decoder
     * @since 1.0
     */
    public MarshallingDecoder getMarshallingDecoder() {
        return marshallingDecoder;
    }
}

