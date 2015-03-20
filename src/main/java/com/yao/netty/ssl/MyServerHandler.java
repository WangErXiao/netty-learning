package com.yao.netty.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by root on 15-3-16.
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelFactory.INSTANCE.remove(ctx.channel());
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = NettyChannelFactory.INSTANCE.get(channelHandlerContext.channel().id().asLongText());
        // 未登录
        if (null == channel) {
            if("login".equals(s)){
                NettyChannelFactory.INSTANCE.add(channelHandlerContext.channel().id().asLongText(),channelHandlerContext.channel());
            }else{
                return;
            }
        }




    }
}
