package com.yao.netty.transferobj;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by root on 15-3-7.
 */
public class ObjectHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----------------->");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        ByteBuf byteBuf=(ByteBuf)msg;
 //       byte[] dest = new byte[byteBuf.readableBytes()];
   //     byteBuf.readBytes(dest);
     //   System.out.println(new String(dest));
        Dog dog=(Dog)msg;
        System.out.println("name:"+dog.getName()+"  age:"+dog.getAge());
        System.out.println("xxx---------");
       // ctx.write("finish--------");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

}
