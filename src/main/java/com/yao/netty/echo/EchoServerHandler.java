package com.yao.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by root on 15-2-11.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ctx.write(msg);
            ctx.flush();
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        channelHandlerContext.writeAndFlush(s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
