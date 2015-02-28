package com.yao.netty.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by root on 15-2-28.
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(1 * 24));
        pipeline.addLast(new FrameHandler());
    }

    public static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{
        @Override
        protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
            System.out.println(new String(byteBuf.array()));
        }
    }
}
