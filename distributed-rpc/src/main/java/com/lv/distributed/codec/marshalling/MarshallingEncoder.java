package com.lv.distributed.codec.marshalling;


import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;


/**
 * 自定义实现编码器
 */
public class MarshallingEncoder {
    /** LENGTH_PLACEHOLDER */
    //4个字节的空数组，用于占位，后续写入key的长度
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    /** Marshaller */
    //获取到序列化器
    private final Marshaller marshaller;

    /**
     * Marshalling encoder
     *
     * @throws IOException io exception
     * @since 1.0
     */
    public MarshallingEncoder() throws IOException {
        //构建一个默认的序列化器
        this.marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    /**
     * Encode
     *
     * @param msg     msg
     * @param byteBuf byte buf
     * @since 1.0
     */
    public void encode(Object msg, ByteBuf byteBuf) {
        try {
            //在序列化对象时，先获取到写的位置
            int lengthPos = byteBuf.writerIndex();
            //填充4个字节的空数据
            byteBuf.writeBytes(LENGTH_PLACEHOLDER);
            //开始序列化对象
            ChannelBufferByteOutput bufferByteOutput = new ChannelBufferByteOutput(byteBuf);
            marshaller.start(bufferByteOutput);
            marshaller.writeObject(msg);
            marshaller.finish();
            /**
             * 在指定索引位置写上指定数据：
             * 例如，lengthPos = 10，写了4个空的数据，这时候 writeBytes=14；假如对象数据写完后 writeBytes=20，那么setInt(10, 20 - 10 - 4) 在索引为10的插入int4个字节的数据6，代表后续对象的长度
             */
            byteBuf.setInt(lengthPos, byteBuf.writerIndex() - lengthPos - 4);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                marshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

