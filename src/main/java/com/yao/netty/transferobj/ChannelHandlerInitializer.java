package com.yao.netty.transferobj;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by root on 15-3-7.
 */
public class ChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline=channel.pipeline();
        pipeline.addLast("encode",new ObjEncoder(Dog.class));
        pipeline.addLast("decode",new ObjDecoder(Dog.class));
        pipeline.addLast(new ObjectHandler());
    }
}
