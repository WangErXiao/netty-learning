package com.yao.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by root on 15-3-1.
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        if(frameLength <=0){
            throw new IllegalArgumentException("frameLength must be positive integer");
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> objects) throws Exception {
        while (byteBuf.readableBytes()>=frameLength){
            ByteBuf buf=byteBuf.readBytes(frameLength);
            objects.add(buf);
        }
    }
}
