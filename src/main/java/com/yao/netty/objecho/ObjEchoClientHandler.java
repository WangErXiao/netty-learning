package com.yao.netty.objecho;

import com.yao.netty.transferobj.Dog;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by root on 15-3-21.
 */
public class ObjEchoClientHandler extends ChannelHandlerAdapter {
   // private final Dog dog;

    public ObjEchoClientHandler() {
       // this.dog = new Dog();
        //dog.setName("wangerxiao");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Dog dog;
        dog = new Dog();
        dog.setName("wangerxiao");
        ctx.writeAndFlush(dog);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
