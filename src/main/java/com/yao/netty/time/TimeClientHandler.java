package com.yao.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.sql.Time;
import java.util.Date;

/**
 * Created by root on 15-2-11.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m=(ByteBuf)msg;
        try {
            long currentTimeMillis=(m.readUnsignedInt()-2208988800L)*1000L;
            System.out.println(new Time(currentTimeMillis));
            ctx.close();
        }finally {
            m.release();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
